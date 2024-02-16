namespace SecVault.MVVM.Model.Form.Validation;

public interface IValidator<in T>
{
    bool IsValid(T value);
}