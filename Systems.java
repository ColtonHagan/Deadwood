class Systems {
    private PlayerModel model;

    public Systems(PlayerModel model) {
        this.model = model;
    }

    public void updateModel(PlayerModel model) {
        this.model = model;
    }

    public boolean checkCanMove() {
        return (!model.getMoved() && model.getCurrentRole() == null);
    }


    public boolean checkCanRehearse() {
        return (model.getCurrentRole() != null);
    }

    public boolean checkCanAct() {
        return (model.getCurrentRole() != null);
    }

    public boolean checkCanAddRole(Role role) {
        return (model.getCurrentRole() == null && role.getUsedBy() == null && model.getRank() >= role.getRank() && (model.getCurrentRoom().getSceneCard().hasRole(role)) || (model.getCurrentRoom().hasRole(role)));
    }

    public boolean checkCanUpgrade() {
        return true;
    }
}