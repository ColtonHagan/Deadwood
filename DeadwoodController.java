import java.util.Random;

class DeadwoodController {
    private PlayerModel model;
    private DeadwoodView view;
    private Systems system;

    public DeadwoodController() {
        view = new DeadwoodView();
        view.addListener(this);
        Systems system = new Systems(model);
        this.system = system;
    }

    public Systems getSystem() {
        return system;
    }

    public DeadwoodView getView() {
        return view;
    }

    public void updateModel(PlayerModel model) {
        this.model = model;
        system.updateModel(model);
    }

    public void getCurrentRoom() {
        view.playerLocation(model.getCurrentRoom().getName());
    }

    public void updateMoney(int money) {
        model.updateMoney(money);
    }

    public void updateCredits(int credits) {
        model.updateCredits(credits);
    }

    public void createOffice(parseData dataParser) throws Exception {
        model.createOffice(dataParser.parseOffice());
    }

    public void upgradeRankCredits(int targetUpgrade) {
        if (system.checkCanUpgrade()) {
            CastingOffice office = model.getOffice();
            if (office.rankPossible(model.getRank(), targetUpgrade)) {
                int cost = office.costCredits(targetUpgrade);
                if (model.getCredits() > cost) {
                    updateCredits(model.getCredits() - cost);
                    view.showUpgradeSuccess(targetUpgrade, model.getRank());
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

    public void upgradeRankDollars(int targetUpgrade) {
        if (system.checkCanUpgrade()) {
            CastingOffice office = model.getOffice();
            if (office.rankPossible(model.getRank(), targetUpgrade)) {
                int cost = office.costDollars(targetUpgrade);
                if (model.getMoney() > cost) {
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
        if (system.checkCanMove()) {
            boolean checkMoved = false;
            String roomName = room.getName();
            for (String s : model.getCurrentRoom().getAdjacentRooms()) {
                if (roomName.equals(s)) {
                    model.updateCurrentRoom(room);
                    checkMoved = true;
                    break;
                }
            }

            if (checkMoved) {
                model.updateMoved(true);
                if(!(model.getCurrentRoom().getSceneCard() == null))
                model.getCurrentRoom().getSceneCard().setFlip(true);
                view.showMoveSuccess(model.getCurrentRoom().getName());
            } else {
                view.showMoveFail(model.getCurrentRoom().getName());
            }
        } else {
            view.printMoveError();
        }
    }

    public void addRole(Role role) {
        if (system.checkCanAddRole(role)) {
            model.takeRole(role);
            role.setUsedBy(model);
            model.getCurrentRoom().removeShotCounter();
            view.showTakeRoleSuccess(role.getName());
            model.updateWorked(true);

        } else {
            view.printAddRoleError();
        }
    }

    public void removeRole() {
        model.removeRole();
    }

    public void rehearse() {
        if (system.checkCanRehearse()) {
            int budget = model.getCurrentRoom().getSceneCard().getBudget();
            if (model.getPracticeChips() + 1 == budget) {
                view.showRehearsalFail(model.getPracticeChips());
            } else {
                model.updatePracticeChips(model.getPracticeChips() + 1);
                view.showRehearsalSuccess(model.getPracticeChips());
                model.updateWorked(true);
            }
        } else {
            view.printRehearseError();
        }
    }

    public void clearMoved() {
        model.updateMoved(false);
    }

    public void clearWorked() {
        model.updateWorked(false);
    }

    private int rollDice() {
        Random rand = new Random();
        return rand.nextInt(6) + 1;
    }

    public void act() {
        int budget = model.getCurrentRoom().getSceneCard().getBudget();
        if ((rollDice() + model.getPracticeChips()) >= budget) {
            //If Actor succeeds in acting:
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
    }
}
