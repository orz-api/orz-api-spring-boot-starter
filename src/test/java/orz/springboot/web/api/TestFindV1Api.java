package orz.springboot.web.api;

import lombok.Data;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import orz.springboot.alarm.exception.OrzAlarmException;
import orz.springboot.alarm.exception.OrzUnexpectedException;
import orz.springboot.web.annotation.OrzWebApi;
import orz.springboot.web.annotation.OrzWebError;
import orz.springboot.web.OrzWebApiException;

import static orz.springboot.base.OrzBaseUtils.hashMap;
import static orz.springboot.base.description.OrzDescriptionUtils.descValues;

@OrzWebApi(domain = "test", action = "find", variant = 1)
public class TestFindV1Api {
    @OrzWebError(code = "1", reason = "test 1")
    @OrzWebError(code = "2", reason = "test 2", alarm = true, logging = true)
    public TestFindV1Rsp request(@Validated @RequestBody TestFindV1Req req) {
        if ("0".equals(req.getTest())) {
            throw new OrzAlarmException("test", hashMap("req", req));
        } else if ("1".equals(req.getTest())) {
            throw new OrzWebApiException("1", descValues("req", req));
        } else if ("2".equals(req.getTest())) {
            throw new OrzWebApiException("1", descValues("req", req), new OrzAlarmException("test", hashMap("req", req)));
        } else if ("3".equals(req.getTest())) {
            throw new OrzUnexpectedException("request error", hashMap("req", req));
        } else if ("4".equals(req.getTest())) {
            throw new OrzWebApiException("not_exists_code");
        } else if ("5".equals(req.getTest())) {
            throw new OrzWebApiException("2", descValues("req", req));
        }
        return new TestFindV1Rsp();
    }

    @Data
    public static class TestFindV1Req {
        private String test;
    }

    @Data
    public static class TestFindV1Rsp {
    }
}
