/*
Name : Colton Hagan and Steven Le
Class : CS 345
Date : 2/23/21
Program Description : Controls player actions and updates view appropriately
*/

import java.util.Random;

class DeadwoodController {
    private PlayerModel model;
    private final DeadwoodView view;
    private final Systems system;

    public DeadwoodController() {
        view = new DeadwoodView();
        this.system = new Systems(model);
    }

    // Getters
    public Systems getSystem() {
        return system;
    }

    public DeadwoodView getView() {
        return view;
    }

    // Setters and Modifiers
    public void clearMoved() {
        model.updateMoved(false);
    }

    public void clearWorked() {
        model.updateWorked(false);
    }

    public void updateModel(PlayerModel model) {
        this.model = model;
        system.updateModel(model);
    }

    public void updateMoney(int money) {
        model.updateMoney(money);
    }

    public void updateCredits(int credits) {
        model.updateCredits(credits);
    }

    public void createOffice(ParseData dataParser) throws Exception {
        model.createOffice(dataParser.parseOffice());
    }

    // Important player action classes: Covers upgrading rank, moving, roles, acting, and rehearsing
    public void upgradeRankCredits(int targetUpgrade, CastingOffice castingOffice) {
        // System check to ensure upgrade is legal (Must be in office)
        if (system.checkCanUpgrade()) {
            CastingOffice office = castingOffice;

            // Ensuring the rank up logic is legal (Can't upgrade to rank 7 or rank down)
            if (system.rankPossible(model.getRank(), targetUpgrade)) {
                int cost = office.costCredits(targetUpgrade);

                // Checking that there is enough credits to rank up
                if (model.getCredits() >= cost) {
                    view.showUpgradeSuccess(targetUpgrade, model.getRank());
                    model.updateRank(targetUpgrade);
                    updateCredits(model.getCredits() - cost);
                } else {
                    view.showUpgradeFail(targetUpgrade);
                }
            } else {
                view.showUpgradeFail(targetUpgrade);
            }
        } else {
            view.printUpgradeError();
        }
    }

    public void upgradeRankDollars(int targetUpgrade, CastingOffice castingOffice) {
        // System check to ensure upgrade is legal (Must be in office)
        if (system.checkCanUpgrade()) {
            CastingOffice office = castingOffice;

            // Ensuring the rank up logic is legal (Can't upgrade to rank 7 or rank down)
            if (system.rankPossible(model.getRank(), targetUpgrade)) {
                int cost = office.costDollars(targetUpgrade);

                // Checking that there is enough cash to rank up
                if (model.getMoney() >= cost) {
                    model.updateRank(targetUpgrade);
                    updateMoney(model.getMoney() - cost);
                    view.showUpgradeSuccess(targetUpgrade, model.getRank());
                } else {
                    view.showUpgradeFail(targetUpgrade);
                }
            }
        } else {
            view.printUpgradeError();
        }
    }

    public void move(Room room) {
        // System check to ensure move is legal
        if (system.checkCanMove()) {
            boolean checkMoved = false;
            String roomName = room.getName();

            // Checking that room is adjacent to at least one of the rooms, then move if true
            for (String s : model.getCurrentRoom().getAdjacentRooms()) {
                if (roomName.equals(s)) {
                    model.updateCurrentRoom(room);
                    checkMoved = true;
                    break;
                }
            }

            // Flipping scene card if the player moved and it was unflipped, printing results via view
            if (checkMoved) {
                model.updateMoved(true);
                if (!(model.getCurrentRoom().getSceneCard() == null))
                    //model.getCurrentRoom().getSceneCard().setFlip(true);
                view.showMoveSuccess(model.getCurrentRoom().getName());
            } else {
                view.showMoveFail(model.getCurrentRoom().getName());
            }
        } else {
            view.printMoveError();
        }
    }

    // System check not here, is done at GameStateController instead, already ensured taking role is legal
    public void addRole(Role role) {
        model.takeRole(role);
        role.setUsedBy(model);
        view.showTakeRoleSuccess(role.getName());
        model.updateHasRole(true);
        model.updateWorked(true);
    }

    public void removeRole() {
        model.removeRole();
    }

    public void rehearse() {
        model.updatePracticeChips(model.getPracticeChips() + 1);
        view.showRehearsalSuccess(model.getPracticeChips());
        model.updateWorked(true);
    }

    private int rollDice() {
        Random rand = new Random();
        return rand.nextInt(6) + 1;
    }

    public boolean act() {
        int budget = model.getCurrentRoom().getSceneCard().getBudget();
        boolean success = false;
        if ((rollDice() + model.getPracticeChips()) >= budget) {
            //If Actor succeeds in acting:
            success = true;
            model.getCurrentRoom().removeShotCounter();
            if (model.getCurrentRole().getExtra()) {
                //Off card:
                model.updateMoney(model.getMoney() + 1);
                model.updateCredits(model.getCredits() + 1);
                view.showActingSuccess(1, 1);
            } else {
                //On card:
                model.updateCredits(model.getCredits() + 2);
                view.showActingSuccess(0, 2);
            }
        } else {
            //If actor fails in acting:
            if (model.getCurrentRole().getExtra()) {
                //Off card:
                model.updateMoney(model.getMoney() + 1);
                view.showActingFail(1, 0);
            } else {
                //On card
                view.showActingFail(0, 0);
            }
        }
        model.updateWorked(true);
        return success;
    }
}
