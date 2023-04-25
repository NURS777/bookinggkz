package kz.diplomaproject.springboot.springDIploma.services;

import org.springframework.stereotype.Service;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OTPService {
    private static final Integer EXPIRE_MINS = 1;
    private LoadingCache<String, Integer> otpCache;
    public OTPService(){
        super();
        otpCache = CacheBuilder.newBuilder().
                expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Integer>() {
                    public Integer load(String key) {
                        return 0;
                    }
                });
    }

    public int generateOTP(String key){
        Random random = new Random();
        int otp = 1000 + random.nextInt(10000);
        otpCache.put(key, otp);
        return otp;
    }

    public int getOtp(String key){
        try{
            return otpCache.get(key);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public String getOtpKey(Integer value){
        String key = "";
        try{
            for(Map.Entry<String, Integer> entry: otpCache.asMap().entrySet()) {
                if(Objects.equals(entry.getValue(), value)) {
                    key = entry.getKey();
                    break;
                }
            }
            return key;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public LoadingCache<String, Integer> getOptCache(){
        return otpCache;
    }

    public void clearOTP(String key){
        otpCache.invalidate(key);
    }
}
