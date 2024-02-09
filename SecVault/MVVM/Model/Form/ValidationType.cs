namespace SecVault.MVVM.Model.Form;

public enum ValidationType
{
    NotNull,
    NotBlank
}

public static class ValidationTypeExtensions
{
    public static Func<T, bool> Validate<T>(this ValidationType type)
    {
        switch (type)
        {
            case ValidationType.NotNull:
                return value => value != null;
            case ValidationType.NotBlank:
                return value => value is string val && !string.IsNullOrEmpty(val);
            default:
                throw new ArgumentOutOfRangeException(nameof(type), type, null);
        }
    }
    
    public static string GetErrorMessage(this ValidationType type, string propertyName)
    {
        switch (type)
        {
            case ValidationType.NotNull:
                return $"{propertyName} must not be null.";
            case ValidationType.NotBlank:
                return $"{propertyName} must not be blank.";
            default:
                throw new ArgumentOutOfRangeException(nameof(type), type, null);
        }
    }
}