import java.util.Random;

class PlayerController {
    private PlayerModel model;
    private PlayerUI view;
    private Systems system;

    public PlayerController(PlayerModel model, PlayerUI view) {
        this.model = model;
        this.view = view;
        Systems system = new Systems(model);
        this.system = system;
        view.addListener(this);
    }

    public void updateModel(PlayerModel model) {
        this.model = model;
        system.updateModel(model);
    }

    public void updateView() {
        view.printPlayerDetails(model.getName(), model.getMoney(), model.getCredits(), model.getRank(), model.getPracticeChips());
    }

    public void getCurrentRoom() {
        view.printRoom(model.getCurrentRoom().getName());
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

    public void upgradeRank(String paymentMethod, int targetUpgrade) {
        if (system.checkCanUpgrade()) {
            CastingOffice office = model.getOffice();
            boolean upgraded = false;
            if (office.rankPossible(model.getRank(), targetUpgrade)) {
                int cost = office.cost(targetUpgrade, paymentMethod);
                if (paymentMethod.equals("credits") && model.getCredits() > cost) {
                    updateCredits(model.getCredits() - cost);
                    upgraded = true;
                } else if (paymentMethod.equals("money") && model.getMoney() > cost) {
                    updateMoney(model.getMoney() - cost);
                    upgraded = true;
                }
            }
            view.showUpgradeResults(upgraded, targetUpgrade, model.getRank());
        } else {
            view.printUpgradeError();
        }
    }

    public void move(Room room) {
        if (system.checkCanMove()) {
            boolean checkChange = false;
            String roomName = room.getName();
            for (String s : model.getCurrentRoom().getAdjacentRooms()) {
                if (roomName.equals(s)) {
                    model.updateCurrentRoom(room);
                    checkChange = true;
                    break;
                }
            }

            if (checkChange) {
                view.showMoveResults(true, model.getCurrentRoom().getName());
            } else {
                view.showMoveResults(false, model.getCurrentRoom().getName());
            }
        } else {
            view.printMoveError();
        }
    }

    public void addRole(Role role) {
        if (system.checkCanAddRole(role)) {
            if (!model.getHasRole() && role.getUsedBy() == null && model.getRank() >= role.getRank() && (model.getCurrentRoom().getSceneCard().hasRole(role)) || (model.getCurrentRoom().hasRole(role))) {
                model.takeRole(role);
                role.setUsedBy(model);
                view.showTakeRoleResults(true, role.getName());
            } else {
                view.showTakeRoleResults(false, role.getName());
            }
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
                view.showRehearsalResults(false, model.getPracticeChips());
            } else {
                model.updatePracticeChips(model.getPracticeChips() + 1);
                view.showRehearsalResults(true, model.getPracticeChips());
            }
        } else {
            view.printRehearseError();
        }
    }

    public void clearRehearse() {
        model.updatePracticeChips(0);
    }

    private int rollDice() {
        Random rand = new Random();
        return rand.nextInt(6) + 1;
    }

    public void act() {
        if (system.checkCanAct()) {
            int budget = model.getCurrentRoom().getSceneCard().getBudget();
            if ((rollDice() + model.getPracticeChips()) >= budget) {
                //If Actor succeeds in acting:
                model.getCurrentRoom().updateShotCounter();


                if (model.getCurrentRole().getExtra()) {
                    //Off card:
                    model.updateMoney(model.getMoney() + 1);
                    model.updateCredits(model.getCredits() + 1);
                    view.showActingResults(true, 1, 1);
                } else {
                    //On card:
                    model.updateCredits(model.getCredits() + 2);
                    view.showActingResults(true, 0, 2);
                }
            } else {
                //If actor fails in acting:
                if (model.getCurrentRole().getExtra()) {
                    //Off card:
                    model.updateMoney(model.getMoney() + 1);
                    view.showActingResults(false, 1, 0);
                } else {
                    //On card
                    view.showActingResults(false, 0, 0);
                }
            }
        } else {
            view.printActError();
        }
    }
}