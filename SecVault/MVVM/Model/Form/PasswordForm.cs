namespace SecVault.MVVM.Model.Form;

public class PasswordForm
{
    public FormInput<string> Username { get; } = new()
    {
        InputName = "Username",
        ValidMessage = "Valid Username.",
        ValidationTypes = [ValidationType.NotNull, ValidationType.NotBlank]
    };
}