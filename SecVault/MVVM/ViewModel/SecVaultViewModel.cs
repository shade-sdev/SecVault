using SecVault.Core;
using SecVault.MVVM.Model.PasswordCategory;

namespace SecVault.MVVM.ViewModel;

public class SecVaultViewModel : ObservableObject
{
    private PasswordCategory? _selectedCategory;

    public List<PasswordCategory> PasswordCategories { get; } = PasswordCategoryProvider.GetList();

    public RelayCommand ClickCommand { get; set; }

    public PasswordCategory? SelectedCategory
    {
        get => _selectedCategory;
        set
        {
            _selectedCategory = value;
            OnPropertyChanged();
        }
    }

    public SecVaultViewModel()
    {
        _selectedCategory = PasswordCategories.FirstOrDefault();
        ClickCommand = new RelayCommand(o => { Console.WriteLine("Jazz"); });
    }
}