using SecVault.Core;

namespace SecVault.MVVM.Model.Form;

/// <summary>
/// Form Object to be used for binding form input and validation
/// </summary>
///
public class FormInput<T> : ObservableObject
{
    private T? _textContent;
    
    public T? TextContent
    {
        get => _textContent;
        set
        {
            _textContent = value;
            AnotherInput?.ValidateCommand.Execute(null); 
        }
    }
    public string? InputName { get; init; }
    
    // Use in rare cases MUST not modify internals
    public FormInput<T>? AnotherInput { get; set; } = null;
    
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
        if (ValidationTypes.Count == 0 || TextContent is null)
        {
            return;
        }
        
        foreach (var validationType in ValidationTypes.Where(validationType =>
                     validationType.Validate(AnotherInput).Invoke(TextContent) == false))
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