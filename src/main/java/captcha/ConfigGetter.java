package captcha;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

class ConfigGetter {
	private ConfigGetter() {}

	static CaptchaConfigBean loadResourceFile() {
		CaptchaConfigBean result = new CaptchaConfigBean();
		try {
			String resourceFilePath = ConfigGetter.class.getResource("config.json").getPath();
			Type listType = new TypeToken<CaptchaConfigBean>(){}.getType();
			StringBuilder sb = new StringBuilder();
			BufferedReader br;
			if (resourceFilePath.contains(".jar!")) {
				br = new BufferedReader(new InputStreamReader(new FileInputStream("config.json"), "UTF-8"));
			} else {
				br = new BufferedReader(new FileReader(resourceFilePath));
			}

			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			result = new Gson().fromJson(sb.toString(), listType);
		} catch(Exception e) {
			System.out.println("JSON GET ERROR");
			e.printStackTrace();
		}
		return result;
	}

}
