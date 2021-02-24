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
        return (model.getHasRole() && model.getWorked());
    }

    // Checking if player has a role
    public boolean checkCanAct() {
        return (model.getHasRole() && model.getWorked());
    }

    /* Checking if
     * 1) model does not have a role
     * 2) role is currently unused
     * 3) model has sufficient rank for role
     * 4) role exists in room, on or off card
     */
    public boolean checkCanAddRole(Role role) {
        // This makes sure there is a scene card. No scene card, no roles to take.
        if (!(model.getCurrentRoom().getSceneCard() == null)) {
            boolean onCard = false;
            if (model.getCurrentRoom().getSceneCard().hasRole(role)) {
                onCard = true;
            }
            return (!model.getHasRole() && role.getUsedBy() == null && model.getRank() >= role.getRank() && (onCard || (model.getCurrentRoom().hasRole(role))));
        } else {
            return false;
        }
    }

    // Checking if player is in office
    public boolean checkCanUpgrade() {
        return (model.getCurrentRoom().getName().equals("office"));
    }

    // Checks if rank upgrade is legal - cannot upgrade to ranks above 6 and
    public boolean rankPossible(int rank, int targetRank) {
        return (rank < 6 && rank < targetRank && targetRank <= 6);
    }

}