package protzek.sebastian.mastermindlogicgame.gameboard;

import java.util.ArrayList;

import protzek.sebastian.mastermindlogicgame.R;
import protzek.sebastian.mastermindlogicgame.gameboard.listeners.CheckIfButtonController;
import protzek.sebastian.mastermindlogicgame.gameboard.listeners.DragListenerDataCatcher;
import protzek.sebastian.mastermindlogicgame.gameboard.math.Comparator;
import protzek.sebastian.mastermindlogicgame.gameboard.math.MasterSetGenerator;
import protzek.sebastian.mastermindlogicgame.gameboard.math.WonOrNot;
import protzek.sebastian.mastermindlogicgame.media.SoundPlayer;

public class GameBoardActivityHelper {
    private MasterSetGenerator generator = new MasterSetGenerator();
    private DragListenerDataCatcher dataCatcher = new DragListenerDataCatcher();
    private CheckIfButtonController controller = new CheckIfButtonController(dataCatcher);
    private Comparator comparator = new Comparator();
    private WonOrNot won = new WonOrNot();
    private GameBoardAdapter adapter;

    private ArrayList<Integer> masterSet = generator.getMasterSet();
    private ArrayList<SingleTurn> game = new ArrayList<>();
    private int indexOfActiveTurn = 0;
    private int turnsLeft;
    private boolean playerWon;

    public GameBoardActivityHelper(SoundPlayer soundPlayer) {
        adapter = new GameBoardAdapter(dataCatcher, soundPlayer, game, indexOfActiveTurn);
    }

    public void createNextTurn() {
        final int icon = R.drawable.empty_circle_golden;
        SingleTurn nextTurn = new SingleTurn(
                icon, icon, icon, icon, icon, icon, icon, icon);
        game.add((nextTurn));
        adapter.notifyItemChanged(game.size());
    }

    public void endTurn() {
        turnsLeft--;
        ArrayList<Integer> playerSet = dataCatcher.getPlayerSet();
        ArrayList<Integer> guessResult = comparator.compareSets(masterSet, playerSet);
        playerWon = won.checkIfWon(guessResult);
        SingleTurn updatedActiveTurn = fillScorePins(guessResult, game.get(indexOfActiveTurn));
        game.set(indexOfActiveTurn, updatedActiveTurn);
        adapter.notifyDataSetChanged();
    }

    public void prepareNextTurn() {
        generator.resetMasterSet();
        dataCatcher.resetPlayerSet();
        createNextTurn();
        indexOfActiveTurn = game.size() - 1;
        adapter.setIndexOfActiveTurn(indexOfActiveTurn);
    }

    private SingleTurn fillScorePins(ArrayList<Integer> guessResult, SingleTurn currentTurn) {
        int counterRightPosition = guessResult.get(0);
        int counterRightNumber = guessResult.get(1);
        ArrayList<Integer> scorePins = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (counterRightPosition > 0) {
                scorePins.add(R.drawable.empty_circle_fire);
                counterRightPosition--;
            } else if (counterRightNumber > 0) {
                scorePins.add(R.drawable.empty_circle_thunder);
                counterRightNumber--;
            } else {
                scorePins.add(R.drawable.empty_circle_golden);
            }
        }
        currentTurn.setFirstScorePin(scorePins.get(0));
        currentTurn.setSecondScorePin(scorePins.get(1));
        currentTurn.setThirdScorePin(scorePins.get(2));
        currentTurn.setFourthScorePin(scorePins.get(3));
        return currentTurn;
    }

    public DragListenerDataCatcher getDataCatcher() {
        return dataCatcher;
    }

    public CheckIfButtonController getController() {
        return controller;
    }

    public int getTurnsLeft() {
        return turnsLeft;
    }

    public void setTurnsLeft(int turnsLeft) {
        this.turnsLeft = turnsLeft;
    }

    public boolean isPlayerWon() {
        return playerWon;
    }

    public int getIndexOfActiveTurn() {
        return indexOfActiveTurn;
    }

    public MasterSetGenerator getGenerator() {
        return generator;
    }

    public GameBoardAdapter getAdapter() {
        return adapter;
    }
}
