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

    public FormInput<string> Password { get; set; }
 

    public FormInput<string> ConfirmPassword { get; set; }

    public PasswordForm()
    {
        Password = new FormInput<string>()
        {
            InputName = "Password",
            ValidMessage = "Valid Password.",
            ValidationTypes = [ValidationType.NotNull, ValidationType.NotBlank, ValidationType.Match]
        };
        
        ConfirmPassword = new FormInput<string>()
        {
            InputName = "Confirm Password",
            ValidMessage = "Password Matches.",
            ValidationTypes = [ValidationType.NotNull, ValidationType.NotBlank, ValidationType.Match]
        };

        Password.AnotherInput = ConfirmPassword;
        ConfirmPassword.AnotherInput = Password;
    }
}