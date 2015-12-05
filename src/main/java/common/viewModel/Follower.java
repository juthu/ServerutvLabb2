package common.viewModel;

/**
 * Created by luben on 2015-12-05.
 */
public class Follower {
    private long me;
    private long following;

    public long getMe() {
        return me;
    }

    public long getFollowing() {
        return following;
    }

    public Follower(long me, long following) {

        this.me = me;
        this.following = following;
    }


}
