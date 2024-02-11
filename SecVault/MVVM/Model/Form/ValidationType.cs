using System.Text.RegularExpressions;

namespace SecVault.MVVM.Model.Form;

/// <summary>
/// Specifies the type of validation to be performed.
/// </summary>
public enum ValidationType
{
    /// <summary>
    /// Validates that the value is not null.
    /// </summary>
    NotNull,

    /// <summary>
    /// Validates that the value is not null or empty.
    /// </summary>
    NotBlank,

    /// <summary>
    /// Validates that the value is a valid email address.
    /// </summary>
    Email,

    /// <summary>
    /// Validates that the value matches another value.
    /// </summary>
    Match,
    
    /// <summary>
    /// Validates that the value is a valid URL.
    /// </summary>
    Url
}

public static class ValidationTypeExtensions
{
    
    // Regular expressions for specific validation types
    private const string VisaCardRegex = "^4[0-9]{12}(?:[0-9]{3})?$";
    private const string MasterCardRegex = "^(?:5[1-5][0-9]{14})$";
    private const string PinRegex = "^[0-9]{3,4}$";
    private const string ValidWebsiteRegex = @"^(http(s)?://)?([\w-]+.)+[\w-]+(/[\w- ;,./?%&=]*)?$";
    private const string ValidEmailRegex = @"^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[\w-]+$";

    /// <summary>
    /// Validates a value based on the specified <paramref name="type"/>.
    /// </summary>
    /// <typeparam name="T">The type of the value to validate.</typeparam>
    /// <param name="type">The type of validation to perform.</param>
    /// <param name="anotherValue">Optional value to compare against for certain validation types.</param>
    /// <returns>A function representing the validation logic.</returns>
    public static Func<T, bool> Validate<T>(this ValidationType type, FormInput<T>? anotherValue = null)
    {
        return type switch
        {
            ValidationType.NotNull => value => value is not null,
            ValidationType.NotBlank => value => value is string val && !string.IsNullOrEmpty(val),
            ValidationType.Email => value => value is string val && RegexMatch(val, ValidEmailRegex),
            ValidationType.Url => value => value is string val && RegexMatch(val, ValidWebsiteRegex),
            ValidationType.Match => value => Match(value, anotherValue),
            _ => throw new ArgumentOutOfRangeException(nameof(type), type, null)
        };
    }

    /// <summary>
    /// Gets the error message corresponding to the specified <paramref name="type"/>.
    /// </summary>
    /// <param name="type">The type of validation.</param>
    /// <param name="propertyName">The name of the property being validated.</param>
    /// <returns>The error message for the validation type.</returns>
    public static string GetErrorMessage(this ValidationType type, string propertyName)
    {
        return type switch
        {
            ValidationType.NotNull => $"{propertyName} must not be null.",
            ValidationType.NotBlank => $"{propertyName} must not be blank.",
            ValidationType.Email => $"{propertyName} must a valid email.",
            ValidationType.Url => $"{propertyName} must a valid url.",
            ValidationType.Match => $"{propertyName} does not match.",
            _ => throw new ArgumentOutOfRangeException(nameof(type), type, null)
        };
    }

    // Helper method to perform regex matching
    private static bool RegexMatch(string value, string regex)
    {
        return new Regex(regex).IsMatch(value);
    }

    // Helper method to perform match validation
    private static bool Match<T>(T value, FormInput<T>? anotherValue)
    {
        if (value is string val
            && anotherValue is not null
            && anotherValue.Content is string anotherVal)
        {
            return val.Equals(anotherVal);
        }

        return value is not null
               && anotherValue is not null
               && anotherValue.Content is not null
               && value.Equals(anotherValue);
    }
}