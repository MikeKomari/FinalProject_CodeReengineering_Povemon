package menu;
import interfaces.MenuAction;
import interfaces.Utility;
import main.GameUI;
public class ExitAction implements MenuAction {
    @Override
    public void execute() {
        Utility.clearScreen();
        new GameUI().printExit();
    }
}
