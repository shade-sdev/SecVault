namespace SecVault.MVVM.Model.Form;

public class PasswordForm
{
    #region Password

    public FormInput<string> Username { get; } = new()
    {
        InputName       = "Username",
        ValidMessage    = "Valid Username.",
        ValidationTypes = [ValidationType.NotNull, ValidationType.NotBlank]
    };

    public FormInput<string> Email { get; } = new()
    {
        InputName       = "Email",
        ValidMessage    = "Valid Email.",
        ValidationTypes = [ValidationType.NotNull, ValidationType.NotBlank, ValidationType.Email]
    };

    public FormInput<string> Password { get; } = new()
    {
        InputName       = "Password",
        ValidMessage    = "Valid Password.",
        ValidationTypes = [ValidationType.NotNull, ValidationType.NotBlank, ValidationType.Match],
    };

    public FormInput<string> ConfirmPassword { get; } = new()
    {
        InputName       = "Confirm Password",
        ValidMessage    = "Password Matches.",
        ValidationTypes = [ValidationType.NotNull, ValidationType.NotBlank, ValidationType.Match]
    };

    public FormInput<string> Name { get; } = new()
    {
        InputName       = "Name",
        ValidMessage    = "Valid Name.",
        ValidationTypes = [ValidationType.NotNull, ValidationType.NotBlank]
    };

    public FormInput<string> Url { get; } = new()
    {
        InputName       = "Url",
        ValidMessage    = "Valid Url.",
        ValidationTypes = [ValidationType.Url]
    };

    public FormInput<string> IconUrl { get; } = new()
    {
        InputName       = "Icon Url",
        ValidMessage    = "Valid Url.",
        ValidationTypes = [ValidationType.Url]
    };

    #endregion

    #region Card

    public FormInput<string> CardNumber { get; } = new()
    {
        InputName       = "Card Number",
        ValidMessage    = "Valid Card Number.",
        ValidationTypes = [ValidationType.NotBlank, ValidationType.Card]
    };
    
    public FormInput<string> Cvc { get; } = new()
    {
        InputName       = "CVC/CCV",
        ValidMessage    = "Valid CVC/CCV Number.",
        ValidationTypes = [ValidationType.NotBlank, ValidationType.Cvc]
    };
    
    public FormInput<string> Pin { get; } = new()
    {
        InputName       = "Pin",
        ValidMessage    = "Valid Pin.",
        ValidationTypes = []
    };

    #endregion

    public PasswordForm()
    {
        Password.AnotherInput        = ConfirmPassword;
        ConfirmPassword.AnotherInput = Password;
    }
}