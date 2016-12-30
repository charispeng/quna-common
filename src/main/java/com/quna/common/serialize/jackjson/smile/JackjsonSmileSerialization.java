package com.quna.common.serialize.jackjson.smile;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.smile.SmileFactory;
import com.quna.common.serialize.jackjson.JackjsonSerialization;

public class JackjsonSmileSerialization extends JackjsonSerialization {
	static{
		setObjectMapper(new ObjectMapper(new SmileFactory()));
	}
}
