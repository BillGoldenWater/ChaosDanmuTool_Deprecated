package indi.goldenwater.chaosdanmutool.model.danmu;

public class GuardBuy extends MessageCommand {
    public long uid;
    public String username;
    public int guard_level;
    public int num;
    public int price;
    public int gift_id;
    public String gift_name;
    public long start_time;
    public long end_time;

    public static String getGuardIconUrl(int level) {
        final String guardIconUrl = "https://i0.hdslb.com/bfs/activity-plat/static/20200716/1d0c5a1b042efb59f46d4ba1286c6727/icon-guard{{lvl}}.png";
        return guardIconUrl.replace("{{lvl}}", String.valueOf(level));
    }
}
