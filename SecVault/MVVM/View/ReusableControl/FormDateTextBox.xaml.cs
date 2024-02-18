using System.Windows;
using SecVault.Core;

namespace SecVault.MVVM.View.ReusableControl;

public partial class FormDateTextBox
{
    public FormDateTextBox()
    {
        InitializeComponent();
    }

    #region Commands

    public static readonly DependencyProperty CommandOneProperty =
        DependencyProperty.Register(nameof(CommandOne), typeof(RelayCommand), typeof(FormDateTextBox));

    public RelayCommand CommandOne
    {
        get => (RelayCommand)GetValue(CommandOneProperty);
        set => SetValue(CommandOneProperty, value);
    }
    
    public static readonly DependencyProperty CommandTwoProperty =
        DependencyProperty.Register(nameof(CommandTwo), typeof(RelayCommand), typeof(FormDateTextBox));

    public RelayCommand CommandTwo
    {
        get => (RelayCommand)GetValue(CommandTwoProperty);
        set => SetValue(CommandTwoProperty, value);
    }

    #endregion

    #region Dimensions

    public new static readonly DependencyProperty WidthProperty =
        DependencyProperty.Register(nameof(Width), typeof(int), typeof(FormDateTextBox));

    public new int Width
    {
        get => (int)GetValue(WidthProperty);
        set => SetValue(WidthProperty, value);
    }

    public new static readonly DependencyProperty BoxOneWidthProperty =
        DependencyProperty.Register(nameof(BoxOneWidth), typeof(int), typeof(FormDateTextBox));

    public new int BoxOneWidth
    {
        get => (int)GetValue(BoxOneWidthProperty);
        set => SetValue(BoxOneWidthProperty, value);
    }

    public new static readonly DependencyProperty BoxTwoWidthProperty =
        DependencyProperty.Register(nameof(BoxTwoWidth), typeof(int), typeof(FormDateTextBox));

    public new int BoxTwoWidth
    {
        get => (int)GetValue(BoxTwoWidthProperty);
        set => SetValue(BoxTwoWidthProperty, value);
    }

    public new static readonly DependencyProperty HeightProperty =
        DependencyProperty.Register(nameof(Height), typeof(int), typeof(FormDateTextBox));

    public new int Height
    {
        get => (int)GetValue(HeightProperty);
        set => SetValue(HeightProperty, value);
    }

    #endregion

    #region Validation

    public static readonly DependencyProperty OneValidProperty =
        DependencyProperty.Register(nameof(OneValid), typeof(bool?), typeof(FormDateTextBox));

    public bool? OneValid
    {
        get => (bool?)GetValue(OneValidProperty);
        set => SetValue(OneValidProperty, value);
    }
    
    public static readonly DependencyProperty TwoValidProperty =
        DependencyProperty.Register(nameof(TwoValid), typeof(bool?), typeof(FormDateTextBox));

    public bool? TwoValid
    {
        get => (bool?)GetValue(TwoValidProperty);
        set => SetValue(TwoValidProperty, value);
    }

    #endregion

    #region Labels

    public static readonly DependencyProperty TextBoxOneLabelProperty =
        DependencyProperty.Register(nameof(TextBoxOneLabel), typeof(string), typeof(FormDateTextBox));

    public string TextBoxOneLabel
    {
        get => (string)GetValue(TextBoxOneLabelProperty);
        set => SetValue(TextBoxOneLabelProperty, value);
    }
    
    public static readonly DependencyProperty TextBoxTwoLabelProperty =
        DependencyProperty.Register(nameof(TextBoxTwoLabel), typeof(string), typeof(FormDateTextBox));

    public string TextBoxTwoLabel
    {
        get => (string)GetValue(TextBoxTwoLabelProperty);
        set => SetValue(TextBoxTwoLabelProperty, value);
    }

    public static readonly DependencyProperty ValidationLabelOneProperty =
        DependencyProperty.Register(nameof(ValidationLabelOne), typeof(string), typeof(FormDateTextBox));

    public string ValidationLabelOne
    {
        get => (string)GetValue(ValidationLabelOneProperty);
        set => SetValue(ValidationLabelOneProperty, value);
    }
    
    public static readonly DependencyProperty ValidationLabelTwoProperty =
        DependencyProperty.Register(nameof(ValidationLabelTwo), typeof(string), typeof(FormDateTextBox));

    public string ValidationLabelTwo
    {
        get => (string)GetValue(ValidationLabelTwoProperty);
        set => SetValue(ValidationLabelTwoProperty, value);
    }

    #endregion

    #region Content

    public static readonly DependencyProperty TextBoxOneContentProperty =
        DependencyProperty.Register(nameof(TextBoxOneContent), typeof(string), typeof(FormDateTextBox));

    public string TextBoxOneContent
    {
        get => (string)GetValue(TextBoxOneContentProperty);
        set => SetValue(TextBoxOneContentProperty, value);
    }

    public static readonly DependencyProperty TextBoxTwoContentProperty =
        DependencyProperty.Register(nameof(TextBoxTwoContent), typeof(string), typeof(FormDateTextBox));

    public string TextBoxTwoContent
    {
        get => (string)GetValue(TextBoxTwoContentProperty);
        set => SetValue(TextBoxTwoContentProperty, value);
    }

    public static readonly DependencyProperty TextBoxOneIconProperty =
        DependencyProperty.Register(nameof(TextOneBoxIcon), typeof(object), typeof(FormDateTextBox));

    public object TextOneBoxIcon
    {
        get => GetValue(TextBoxOneIconProperty);
        set => SetValue(TextBoxOneIconProperty, value);
    }

    public static readonly DependencyProperty TextBoxTwoIconProperty =
        DependencyProperty.Register(nameof(TextTwoBoxIcon), typeof(object), typeof(FormDateTextBox));

    public object TextTwoBoxIcon
    {
        get => GetValue(TextBoxTwoIconProperty);
        set => SetValue(TextBoxTwoIconProperty, value);
    }

    public static readonly DependencyProperty TextBoxOnePlaceHolderProperty =
        DependencyProperty.Register(nameof(TextOneBoxPlaceHolder), typeof(string), typeof(FormDateTextBox));

    public string TextOneBoxPlaceHolder
    {
        get => (string)GetValue(TextBoxOnePlaceHolderProperty);
        set => SetValue(TextBoxOnePlaceHolderProperty, value);
    }

    public static readonly DependencyProperty TextBoxTwoPlaceHolderProperty =
        DependencyProperty.Register(nameof(TextTwoBoxPlaceHolder), typeof(string), typeof(FormDateTextBox));

    public string TextTwoBoxPlaceHolder
    {
        get => (string)GetValue(TextBoxTwoPlaceHolderProperty);
        set => SetValue(TextBoxTwoPlaceHolderProperty, value);
    }

    #endregion

    #region Font

    public static readonly DependencyProperty TextBoxFontFamilyProperty =
        DependencyProperty.Register(nameof(TextBoxFontFamily), typeof(string), typeof(FormDateTextBox));

    public string TextBoxFontFamily
    {
        get => (string)GetValue(TextBoxFontFamilyProperty);
        set => SetValue(TextBoxFontFamilyProperty, value);
    }

    #endregion
}