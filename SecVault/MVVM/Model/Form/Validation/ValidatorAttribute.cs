namespace SecVault.MVVM.Model.Form.Validation;

public class ValidatorAttribute<T> : IValidator<T>
{
    public ValidationType ValidationType { get; }
    private int?           MaxSize        { get; }
    private string?        AnotherValue   { get; }
    
    public ValidatorAttribute(ValidationType validationType)
    {
        ValidationType = validationType;
    }
    
    public ValidatorAttribute(ValidationType validationType, int? maxSize = null)
    {
        ValidationType = validationType;
        MaxSize        = maxSize;
    }

    public ValidatorAttribute(ValidationType validationType, string? anotherValue = null)
    {
        ValidationType = validationType;
        AnotherValue   = anotherValue;
    }

    public bool IsValid(T value)
    {
        switch (ValidationType)
        {
            case ValidationType.NotNull:
            case ValidationType.NotBlank:
            case ValidationType.Email:
            case ValidationType.Url:
            case ValidationType.Card:
            case ValidationType.Cvc:
                return ValidationType.Validate<T>().Invoke(value);
            case ValidationType.Match:
                return ValidationType.Validate<T>(AnotherValue).Invoke(value);
            case ValidationType.MaxSize:
                return ValidationType.Validate<T>(MaxSize).Invoke(value);
            default: throw new ArgumentOutOfRangeException(nameof(value), value, null);
        }
    }
}