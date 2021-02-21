class Systems {
    private PlayerModel model;

    public Systems(PlayerModel model) {
        this.model = model;
    }

    public void updateModel(PlayerModel model) {
        this.model = model;
    }

    public boolean checkCanMove() {
        return (!model.getMoved() && !model.getHasRole());
    }


    public boolean checkCanRehearse() {
        return (model.getHasRole());
    }

    public boolean checkCanAct() {
        return (model.getHasRole());
    }

    public boolean checkCanAddRole(Role role) {
        return (!model.getHasRole() && role.getUsedBy() == null && model.getRank() >= role.getRank() && (model.getCurrentRoom().getSceneCard().hasRole(role)) || (model.getCurrentRoom().hasRole(role)));
    }

    public boolean checkCanUpgrade() {
        return true;
    }
}