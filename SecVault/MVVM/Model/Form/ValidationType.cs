using System.Text.RegularExpressions;

namespace SecVault.MVVM.Model.Form;

public enum ValidationType
{
    NotNull,
    NotBlank,
    Email,
    Match
}

public static class ValidationTypeExtensions
{
    private const string VisaCardRegex = "^4[0-9]{12}(?:[0-9]{3})?$";
    private const string MasterCardRegex = "^(?:5[1-5][0-9]{14})$";
    private const string PinRegex = "^[0-9]{3,4}$";
    private const string ValidWebsiteRegex = @"^(http(s)?://)?([\w-]+.)+[\w-]+(/[\w- ;,./?%&=]*)?$";
    private const string ValidEmailRegex = @"^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[\w-]+$";

    public static Func<T, bool> Validate<T>(this ValidationType type, FormInput<T>? anotherValue = null)
    {
        return type switch
        {
            ValidationType.NotNull => value => value is not null,
            ValidationType.NotBlank => value => value is string val && !string.IsNullOrEmpty(val),
            ValidationType.Email => value => value is string val && RegexMatch(val, ValidEmailRegex),
            ValidationType.Match => value => Match(value, anotherValue),
            _ => throw new ArgumentOutOfRangeException(nameof(type), type, null)
        };
    }

    public static string GetErrorMessage(this ValidationType type, string propertyName)
    {
        return type switch
        {
            ValidationType.NotNull => $"{propertyName} must not be null.",
            ValidationType.NotBlank => $"{propertyName} must not be blank.",
            ValidationType.Email => $"{propertyName} must a valid email.",
            ValidationType.Match => $"{propertyName} does not match.",
            _ => throw new ArgumentOutOfRangeException(nameof(type), type, null)
        };
    }

    private static bool RegexMatch(string value, string regex)
    {
        return new Regex(regex).IsMatch(value);
    }

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