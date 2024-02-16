using SecVault.Core;
using SecVault.MVVM.Model.Form.Validation;

namespace SecVault.MVVM.Model.Form;

/// <summary>
///     Represents an input field in a form, including its value, validation rules, and validation status.
/// </summary>
/// <typeparam name="T">The type of content stored in the input field.</typeparam>
public class FormInput<T> : ObservableObject
{
    private T? _content;

    /// <summary>
    ///     Initializes a new instance of the <see cref="FormInput{T}" /> class.
    /// </summary>
    public FormInput()
    {
        ValidateCommand = new RelayCommand(TriggerValidation);
    }

    /// <summary>
    ///     Gets or sets the content of the input field.
    /// </summary>
    public T? Content
    {
        get => _content;
        set
        {
            _content = value;
        }
    }

    /// <summary>
    ///     Gets or initializes the name of the input field.
    /// </summary>
    public string? InputName { get; init; }
    
    /// <summary>
    ///     Gets or initializes the valid message for the input field.
    /// </summary>
    public string? ValidMessage { get; init; }

    /// <summary>
    ///     Gets or sets an explicit message related to the validation status of the input field.
    /// </summary>
    public string? ExplicitMessage { get; set; }

    /// <summary>
    ///     Gets or sets a value indicating whether the input field is valid according to its validation rules.
    /// </summary>
    public bool? IsValid { get; set; }

    /// <summary>
    ///     Gets or initializes a list of validation types to be applied to the input field.
    /// </summary>
    public List<ValidatorAttribute<T>> ValidatorAttributes { get; set; } = [];

    /// <summary>
    ///     Gets or sets the command to trigger validation of the input field.
    /// </summary>
    public RelayCommand ValidateCommand { get; set; }

    /// <summary>
    ///     Triggers validation of the input field based on its validation rules.
    /// </summary>
    /// <param name="parameter">An optional parameter for command execution.</param>
    private void TriggerValidation(object parameter)
    {
        if (ValidatorAttributes.Count == 0 || Content is null) return;

        foreach (var validatorAttribute in ValidatorAttributes.Where(validatorAttribute =>
                                                                         validatorAttribute.IsValid(Content) == false))
        {
            IsValid         = false;
            ExplicitMessage = validatorAttribute.ValidationType.GetErrorMessage(InputName ?? "Field");
            PropertyChanges();
            return;
        }

        IsValid         = true;
        ExplicitMessage = ValidMessage;
        PropertyChanges();
    }

    /// <summary>
    ///     Notifies of property changes.
    /// </summary>
    private void PropertyChanges()
    {
        OnPropertyChanged(nameof(Content));
        OnPropertyChanged(nameof(IsValid));
        OnPropertyChanged(nameof(ExplicitMessage));
    }
}