using System.Text.RegularExpressions;

namespace SecVault.MVVM.Model.Form;

/// <summary>
///     Specifies the type of validation to be performed.
/// </summary>
public enum ValidationType
{
    /// <summary>
    ///     Validates that the value is not null.
    /// </summary>
    NotNull,

    /// <summary>
    ///     Validates that the value is not null or empty.
    /// </summary>
    NotBlank,

    /// <summary>
    ///     Validates that the value is a valid email address.
    /// </summary>
    Email,

    /// <summary>
    ///     Validates that the value matches another value.
    /// </summary>
    Match,

    /// <summary>
    ///     Validates that the value is a valid URL.
    /// </summary>
    Url,

    /// <summary>
    ///     Validates that the value is a Card.
    /// </summary>
    Card,

    /// <summary>
    ///     Validates that the value is a CVC/CVV.
    /// </summary>
    Cvc,
    
    /// <summary>
    ///     Validates that the value is less or equals to the MaxSize.
    /// </summary>
    MaxSize
}

public static class ValidationTypeExtensions
{
    // Regular expressions for specific validation types
    private const string VisaCardRegex     = "^4[0-9]{12}(?:[0-9]{3})?$";
    private const string MasterCardRegex   = "^(?:5[1-5][0-9]{14})$";
    private const string PinRegex          = "^[0-9]{3,4}$";
    private const string ValidWebsiteRegex = @"^(http(s)?://)?([\w-]+.)+[\w-]+(/[\w- ;,./?%&=]*)?$";
    private const string ValidEmailRegex   = @"^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[\w-]+$";

    /// <summary>
    ///     Validates a value based on the specified <paramref name="type" />.
    /// </summary>
    /// <typeparam name="T">The type of the value to validate.</typeparam>
    /// <param name="type">The type of validation to perform.</param>
    /// <returns>A function representing the validation logic.</returns>
    public static Func<T, bool> Validate<T>(this ValidationType type)
    {
        return type switch
               {
                   ValidationType.NotNull => value => value is not null,
                   ValidationType.NotBlank => value => value is string val && !string.IsNullOrEmpty(val),
                   ValidationType.Email => value => value is string val && RegexMatch(val, ValidEmailRegex),
                   ValidationType.Url => value => value is string val && RegexMatch(val, ValidWebsiteRegex),
                   ValidationType.Card => value => RegexAnyMatch(value?.ToString(), [VisaCardRegex, MasterCardRegex]),
                   ValidationType.Cvc => value => value is string val && RegexMatch(val, PinRegex),
                   _ => throw new ArgumentOutOfRangeException(nameof(type), type, null)
               };
    }

    public static Func<T, bool> Validate<T>(this ValidationType type, object? anotherValue)
    {
        return type switch
               {
                   ValidationType.Match => value => Match(value, anotherValue),
                   ValidationType.MaxSize => value => MaxSize(value, anotherValue),
                   _ => throw new ArgumentOutOfRangeException(nameof(type), type, null)
               };
    }

    /// <summary>
    ///     Gets the error message corresponding to the specified <paramref name="type" />.
    /// </summary>
    /// <param name="type">The type of validation.</param>
    /// <param name="propertyName">The name of the property being validated.</param>
    /// <returns>The error message for the validation type.</returns>
    public static string GetErrorMessage(this ValidationType type, string propertyName)
    {
        return type switch
               {
                   ValidationType.NotNull  => $"{propertyName} must not be null.",
                   ValidationType.NotBlank => $"{propertyName} must not be blank.",
                   ValidationType.Email    => $"{propertyName} must a valid email.",
                   ValidationType.Url      => $"{propertyName} must a valid url.",
                   ValidationType.Match    => $"{propertyName} does not match.",
                   ValidationType.Card     => $"{propertyName} must be a valid card number.",
                   ValidationType.Cvc      => $"{propertyName} must be a valid CVC/CVV number.",
                   ValidationType.MaxSize  => $"{propertyName} must be of a valid length.",
                   _                       => throw new ArgumentOutOfRangeException(nameof(type), type, null)
               };
    }

    // Helper method to perform regex matching
    private static bool RegexMatch(string value, string regex)
    {
        return new Regex(regex).IsMatch(value);
    }

    private static bool RegexAnyMatch(string? value, IEnumerable<string> regexes)
    {
        return regexes.Select(regex => new Regex(regex))
                      .Any(regex => value != null && regex.IsMatch(value));
    }

    private static bool MaxSize<T>(T? value, object? size)
    {
        if (value is null || size is null)
        {
            return false;
        }

        return value.ToString()!.Length <= Convert.ToInt32(size);
    }

    // Helper method to perform match validation
    private static bool Match<T>(T? value, T? anotherValue)
    {
        if (value is string val
            && anotherValue is not null
            && anotherValue is string anotherVal)
            return val.Equals(anotherVal);

        return value is not null
               && anotherValue is not null
               && value.Equals(anotherValue);
    }
}