/*
Name : Colton Hagan and Steven Le
Class : CS 345
Date : 2/23/21
Program Description : Verifies certain actions are valid
*/
class Systems {
    private PlayerModel model;

    public Systems(PlayerModel model) {
        this.model = model;
    }

    public void updateModel(PlayerModel model) {
        this.model = model;
    }

    // Checking if already moved and that player does not have role
    public boolean checkCanMove() {
        return (!model.getMoved() && !model.getHasRole());
    }

    // Checking if player has a role, if they do return true since they can rehearse
    public boolean checkCanRehearse() {
        int budget = model.getCurrentRoom().getSceneCard().getBudget();
        return (model.getHasRole() && model.getWorked() && !(model.getPracticeChips() + 1 >= budget));
    }

    // Checking if player has a role, if they do return true since they can act
    public boolean checkCanAct() {
        return (model.getHasRole() && model.getWorked());
    }

    //Checking if model has a role, if they do return false since cannot take roles
    public boolean checkCanAddRole() {
        return !model.getHasRole();
    }

    /* Checking if
     * 1) model does not have a role
     * 2) role is currently unused
     * 3) model has sufficient rank for role
     * 4) role exists in room, on or off card
     */
    public boolean checkRoleValid(Role role) {
        // This makes sure there is a scene card. No scene card, no roles to take.
        if (!(model.getCurrentRoom().getSceneCard() == null)) {
            return (role.getUsedBy() == null && model.getRank() >= role.getRank());
        } else {
            return false;
        }
    }

    // Checking if player is in office
    public boolean checkCanUpgrade() {
        return (model.getCurrentRoom().getName().equals("office"));
    }

    // Checks if rank upgrade is legal - cannot upgrade to ranks above 6 and cannot rank down
    public boolean rankPossible(int rank, int targetRank) {
        return (rank < targetRank && targetRank <= 6);
    }

}