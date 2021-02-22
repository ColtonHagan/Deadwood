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

    // Checking if player has a role
    public boolean checkCanRehearse() {
        return (model.getHasRole());
    }

    // Checking if player has a role
    public boolean checkCanAct() {
        return (model.getHasRole());
    }

    /* Checking if
     * 1) model does not have a role
     * 2) role is currently unused
     * 3) model has sufficient rank for role
     * 4) role exists in room, on or off card
     */
    public boolean checkCanAddRole(Role role) {
        return (!model.getHasRole() && role.getUsedBy() == null && model.getRank() >= role.getRank() && (model.getCurrentRoom().getSceneCard().hasRole(role)) || (model.getCurrentRoom().hasRole(role)));
    }

    // Checking if player is in office
    public boolean checkCanUpgrade() {
        return (model.getCurrentRoom().getName().equals("office"));
    }
}