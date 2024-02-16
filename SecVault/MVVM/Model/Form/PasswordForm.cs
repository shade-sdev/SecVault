using System.ComponentModel;
using SecVault.MVVM.Model.Form.Validation;

namespace SecVault.MVVM.Model.Form;

public class PasswordForm
{
    public PasswordForm()
    {
        // Set up validators for Password and ConfirmPassword
        Password.ValidatorAttributes.Add(new ValidatorAttribute<string>(ValidationType.NotBlank));
        ConfirmPassword.ValidatorAttributes.Add(new ValidatorAttribute<string>(ValidationType.NotBlank));
        
        Password.PropertyChanged        += PasswordPropertyChanged!;
    }

    private void PasswordPropertyChanged(object sender, PropertyChangedEventArgs e)
    {
        // Check if the property changed is Content
        if (e.PropertyName == nameof(FormInput<string>.Content) && sender is FormInput<string> pass &&
            pass.InputName == nameof(Password))
        {

            if (ConfirmPassword.ValidatorAttributes.Count >= 2)
            {
                ConfirmPassword.ValidatorAttributes[1] =
                    new ValidatorAttribute<string>(ValidationType.Match, Password.Content);
            }
            else
            {
                ConfirmPassword.ValidatorAttributes.Add(new ValidatorAttribute<string>(ValidationType.Match,
                                                                                       Password.Content));
            }
            ConfirmPassword.ValidateCommand.Execute(ConfirmPassword.Content);
        }
        
    }

    #region Password

    public FormInput<string> Username { get; } = new()
    {
        InputName           = "Username",
        ValidMessage        = "Valid Username.",
        ValidatorAttributes = [new ValidatorAttribute<string>(ValidationType.NotBlank)]
    };

    public FormInput<string> Email { get; } = new()
    {
        InputName    = "Email",
        ValidMessage = "Valid Email.",
        ValidatorAttributes =
        [
            new ValidatorAttribute<string>(ValidationType.NotBlank),
            new ValidatorAttribute<string>(ValidationType.Email)
        ]
    };

    public FormInput<string> Password { get; } = new()
    {
        InputName    = "Password",
        ValidMessage = "Valid Password."
    };

    public FormInput<string> ConfirmPassword { get; } = new()
    {
        InputName    = "ConfirmPassword",
        ValidMessage = "Password Matches.",
    };

    public FormInput<string> Name { get; } = new()
    {
        InputName           = "Name",
        ValidMessage        = "Valid Name.",
        ValidatorAttributes = [new ValidatorAttribute<string>(ValidationType.NotBlank)]
    };

    public FormInput<string> Url { get; } = new()
    {
        InputName           = "Url",
        ValidMessage        = "Valid Url.",
        ValidatorAttributes = [new ValidatorAttribute<string>(ValidationType.Url)]
    };

    public FormInput<string> IconUrl { get; } = new()
    {
        InputName           = "Icon Url",
        ValidMessage        = "Valid Url.",
        ValidatorAttributes = [new ValidatorAttribute<string>(ValidationType.Url)]
    };

    #endregion

    #region Card

    public FormInput<string> CardNumber { get; } = new()
    {
        InputName    = "Card Number",
        ValidMessage = "Valid Card Number.",
        ValidatorAttributes =
        [
            new ValidatorAttribute<string>(ValidationType.NotBlank), new ValidatorAttribute<string>(ValidationType.Card)
        ]
    };

    public FormInput<string> Cvc { get; } = new()
    {
        InputName    = "CVC/CCV",
        ValidMessage = "Valid CVC/CCV Number.",
        ValidatorAttributes =
        [
            new ValidatorAttribute<string>(ValidationType.NotBlank), new ValidatorAttribute<string>(ValidationType.Cvc)
        ]
    };

    public FormInput<string> Pin { get; } = new()
    {
        InputName           = "Pin",
        ValidMessage        = "Valid Pin.",
        ValidatorAttributes = []
    };

    public FormInput<string> Notes { get; } = new()
    {
        InputName           = "Notes",
        ValidMessage        = "Valid Note.",
        ValidatorAttributes = []
    };

    #endregion
}