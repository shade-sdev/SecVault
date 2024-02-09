using SecVault.Core;

namespace SecVault.MVVM.Model.Form;

/// <summary>
/// Form Object to be used for binding form input and validation
/// </summary>
///
public class FormInput<T> : ObservableObject
{
    public T? TextContent { get; set; }

    public string? ErrorMessage { get; init; }
    public string? ValidMessage { get; init; }
    public string? ExplicitMessage { get; set; }

    public bool? IsValid { get; set; }
    public Func<T, bool>? Validate { get; init; }

    public RelayCommand ValidateCommand { get; set; }

    public FormInput()
    {
        ValidateCommand = new RelayCommand(TriggerValidation);
    }

    private void TriggerValidation(object parameter)
    {
        if (Validate != null && parameter is T param)
        {
            IsValid = Validate.Invoke(param);
            ExplicitMessage = (bool)IsValid ? ValidMessage : ErrorMessage;
            OnPropertyChanged(nameof(IsValid));
            OnPropertyChanged(nameof(ExplicitMessage));
        }
    }
}