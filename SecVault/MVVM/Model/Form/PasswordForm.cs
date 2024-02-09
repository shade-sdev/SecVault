namespace SecVault.MVVM.Model.Form;

public class PasswordForm
{
    public FormInput<string> Username { get; } = new()
    {
        InputName = "Username",
        ValidMessage = "Valid Username.",
        ValidationTypes = [ValidationType.NotNull, ValidationType.NotBlank]
    };

    public FormInput<string> Email { get; } = new()
    {
        InputName = "Email",
        ValidMessage = "Valid Email.",
        ValidationTypes = [ValidationType.NotNull, ValidationType.NotBlank, ValidationType.Email]
    };

    public FormInput<string> Password { get; } = new()
    {
        InputName = "Password",
        ValidMessage = "Valid Password.",
        ValidationTypes = [ValidationType.NotNull, ValidationType.NotBlank, ValidationType.Match],
    };

    public FormInput<string> ConfirmPassword { get; } = new()
    {
        InputName = "Confirm Password",
        ValidMessage = "Password Matches.",
        ValidationTypes = [ValidationType.NotNull, ValidationType.NotBlank, ValidationType.Match]
    };

    public PasswordForm()
    {
        Password.AnotherInput = ConfirmPassword;
        ConfirmPassword.AnotherInput = Password;
    }
}