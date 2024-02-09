using SecVault.Core;

namespace SecVault.MVVM.Model.Form;

/// <summary>
/// Form Object to be used for binding form input and validation
/// </summary>
///
public class FormInput<T> : ObservableObject
{
    public T? TextContent { get; set; }
    public string? InputName { get; init; }

    public string? ValidMessage { get; init; }
    public string? ExplicitMessage { get; set; }

    public bool? IsValid { get; set; }
    public List<ValidationType> ValidationTypes { get; init; } = [];

    public RelayCommand ValidateCommand { get; set; }

    public FormInput()
    {
        ValidateCommand = new RelayCommand(TriggerValidation);
    }

    private void TriggerValidation(object parameter)
    {
        if (ValidationTypes.Count == 0 || parameter is not T param)
        {
            return;
        }

        foreach (var validationType in ValidationTypes.Where(validationType =>
                     validationType.Validate<T>().Invoke(param) == false))
        {
            IsValid = false;
            ExplicitMessage = validationType.GetErrorMessage(InputName ?? "Field");
            PropertyChanges();
            return;
        }

        IsValid = true;
        ExplicitMessage = ValidMessage;
        PropertyChanges();
    }

    private void PropertyChanges()
    {
        OnPropertyChanged(nameof(IsValid));
        OnPropertyChanged(nameof(ExplicitMessage));
    }
}