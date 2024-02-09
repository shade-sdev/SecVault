namespace SecVault.MVVM.Model.Form;

public class PasswordForm
{
    public FormInput<string> Username { get; set; }

    public PasswordForm()
    {
        Username = new FormInput<string>()
        {
            ErrorMessage = "Invalid Username.",
            ValidMessage = "Valid Username.",
            Validate = content => !string.IsNullOrEmpty(content)
        };
    }
}