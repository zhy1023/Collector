package data.mmkv;

/**
 * Author:zhy
 * Date:2022/3/8
 * Description:AppMMKVData ：全局MMKV
 */
public class AppMMKV extends BaseMMKV {
    private static final String mmkvID = "mmkv_app";

    public AppMMKV(String mmkvId) {
        super(mmkvId);
    }

    private static class SingletonHolder {
        private static final AppMMKV mInstance = new AppMMKV(mmkvID);
    }

    public static AppMMKV getInstance() {
        return SingletonHolder.mInstance;
    }
}
