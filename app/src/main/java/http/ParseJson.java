package Http;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.net.ParseException;
import android.util.Log;

public class ParseJson {

	private final String TAG = "ParseJson";

	public ParseJson() {
		super();
	}

	public boolean getLoginResult(String reslut) {

		// Log.d(TAG, "=====" + reslut);
		Boolean success = false;

		try {
			JSONObject jsonObject = new JSONObject(reslut);
			if (jsonObject.has("success")) {
				success = jsonObject.getBoolean("success");
			} else {
				Log.d(TAG, "The input data has not contain \"success\" key");
			}

			String text = null;
			if (jsonObject.has("text")) {
				text = jsonObject.getString("text");
			} else {
				Log.d(TAG, "The input data has not contain \"text\" key");
			}
			// Log.d(TAG, "Login text:" + text + " ;");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			success = false;
		}

		return success;
	}

	public String getLoginFailReason(String reslut) {

		// Log.d(TAG, "=====" + reslut);
		String text = null;
		Boolean success = false;

		try {
			JSONObject jsonObject = new JSONObject(reslut);
			if (jsonObject.has("success")) {
				success = jsonObject.getBoolean("success");
			} else {
				Log.d(TAG, "The input data has not contain \"success\" key");
			}

			if (!success && jsonObject.has("text")) {
				text = jsonObject.getString("text");
			} else {
				Log.d(TAG, "The input data has not contain \"text\" key");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			text = "����ʧ�ܣ�";
		}

		return text;
	}

	public HashMap<String, Object> getAccountInfo(String reslut) {

		HashMap<String, Object> accountInfoMap = new HashMap<String, Object>();
		try {
			JSONObject jsonObject = new JSONObject(reslut);
			JSONObject childJsonObject = (JSONObject) jsonObject
					.get("userinfo");

			if (childJsonObject.has("Account")) {
				accountInfoMap.put("Account",
						childJsonObject.getString("Account"));
			} else {
				Log.d(TAG, "The input data has not contain \"Account\" key");
			}
			if (childJsonObject.has("DisplayName")) {
				accountInfoMap.put("DisplayName",
						childJsonObject.getString("DisplayName"));
			} else {
				Log.d(TAG, "The input data has not contain \"DisplayName\" key");
			}
			if (childJsonObject.has("HRID")) {
				accountInfoMap.put("HRID", childJsonObject.getString("HRID"));
			} else {
				Log.d(TAG, "The input data has not contain \"HRID\" key");
			}
			if (childJsonObject.has("Mobile")) {
				accountInfoMap.put("Mobile",
						childJsonObject.getString("Mobile"));
			} else {
				Log.d(TAG, "The input data has not contain \"Mobile\" key");
			}
			if (childJsonObject.has("OfficePhone")) {
				accountInfoMap.put("OfficePhone",
						childJsonObject.getString("OfficePhone"));
			} else {
				Log.d(TAG, "The input data has not contain \"OfficePhone\" key");
			}
			if (childJsonObject.has("EMail")) {
				accountInfoMap.put("EMail", childJsonObject.getString("EMail"));
			} else {
				Log.d(TAG, "The input data has not contain \"EMail\" key");
			}
			if (childJsonObject.has("Birthday")) {
				accountInfoMap.put("Birthday",
						childJsonObject.getString("Birthday"));
			} else {
				Log.d(TAG, "The input data has not contain \"Birthday\" key");
			}
			if (childJsonObject.has("Office")) {
				accountInfoMap.put("Office",
						childJsonObject.getString("Office"));
			} else {
				Log.d(TAG, "The input data has not contain \"EMail\" key");
			}
			if (childJsonObject.has("DateHired")) {
				accountInfoMap.put("DateHired",
						childJsonObject.getString("DateHired"));
			} else {
				Log.d(TAG, "The input data has not contain \"DateHired\" key");
			}
			if (childJsonObject.has("Description")) {
				accountInfoMap.put("Description",
						childJsonObject.getString("Description"));
			} else {
				Log.d(TAG, "The input data has not contain \"Description\" key");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return accountInfoMap;
	}

	// �洬����Ϣ
	public ArrayList<HashMap<String, String>> getbasicShipInfo(String result) {

		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

		JSONObject jsonObject;
		JSONArray jsonArray;
		try {
			jsonObject = new JSONObject(result);
			String state = null;
			if (jsonObject.has("STATE")) {
				state = jsonObject.getString("STATE");
			}

			if ("1".equalsIgnoreCase(state) && jsonObject.has("SHIP_INFO")) {

				jsonArray = jsonObject.getJSONArray("SHIP_INFO");
				for (int i = 0; i < jsonArray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject object = jsonArray.getJSONObject(i);
					if (object.has("SHIP_NAME")) {
						map.put("SHIP_NAME", object.getString("SHIP_NAME"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("SHIP_NO")) {
						map.put("SHIP_NO", object.getString("SHIP_NO"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("SHIP_PORT")) {
						map.put("SHIP_PORT", object.getString("SHIP_PORT"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("SHIP_DISTRICT")) {
						map.put("SHIP_DISTRICT",
								object.getString("SHIP_DISTRICT"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("JOB_TYPE")) {
						map.put("JOB_TYPE", object.getString("JOB_TYPE"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("SHIP_TOT_POWER")) {
						map.put("SHIP_TOT_POWER",
								object.getString("SHIP_TOT_POWER"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("SHIP_BUILD_COMP_DATE")) {
						String temp = object.getString("SHIP_BUILD_COMP_DATE");
						map.put("SHIP_BUILD_COMP_DATE", temp);
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("OWNER_NAME")) {
						map.put("OWNER_NAME", object.getString("OWNER_NAME"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("SHIP_BUSINESS_TYPE")) {
						map.put("SHIP_BUSINESS_TYPE",
								object.getString("SHIP_BUSINESS_TYPE"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("OWN_DICT_SHIP_MATERIA")) {
						map.put("OWN_DICT_SHIP_MATERIA",
								object.getString("OWN_DICT_SHIP_MATERIA"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("SHIP_TOT_TON")) {
						map.put("SHIP_TOT_TON",
								object.getString("SHIP_TOT_TON"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("SHIP_LENGTH")) {
						map.put("SHIP_LENGTH", object.getString("SHIP_LENGTH"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("SHIP_TOT_LENGHT")) {
						map.put("SHIP_TOT_LENGHT",
								object.getString("SHIP_TOT_LENGHT"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("SHIP_WIDTH")) {
						map.put("SHIP_WIDTH", object.getString("SHIP_WIDTH"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("SHIP_DEEP")) {
						map.put("SHIP_DEEP", object.getString("SHIP_DEEP"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}

					list.add(map);
				}

			} else {
				return null;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return list;
	}

	// ��������Ϣ
	public ArrayList<HashMap<String, String>> getShipContactsInfo(String result) {

		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

		JSONObject jsonObject;
		JSONArray jsonArray;
		try {
			jsonObject = new JSONObject(result);
			String state = null;
			if (jsonObject.has("STATE")) {
				state = jsonObject.getString("STATE");
			}

			if ("1".equalsIgnoreCase(state) && jsonObject.has("SHIP_INFO")) {

				jsonArray = jsonObject.getJSONArray("SHIP_INFO");
				for (int i = 0; i < jsonArray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject object = jsonArray.getJSONObject(i);

					if (object.has("ID")) {
						map.put("ID", object.getString("ID"));
					} else {
						Log.d(TAG, "ID Not exist this key!");
					}
					if (object.has("SHIP_NAME")) {
						map.put("SHIP_NAME", object.getString("SHIP_NAME"));
					} else {
						Log.d(TAG, "SHIP_NAME Not exist this key!");
					}
					if (object.has("SHIP_NO")) {
						map.put("SHIP_NO", object.getString("SHIP_NO"));
					} else {
						Log.d(TAG, "SHIP_NO Not exist this key!");
					}
					if (object.has("CONTACTS")) {
						map.put("CONTACTS", object.getString("CONTACTS"));
					} else {
						Log.d(TAG, "CONTACTS Not exist this key!");
					}
					if (object.has("CONTACTS_TEL")) {
						map.put("CONTACTS_TEL",
								object.getString("CONTACTS_TEL"));
					} else {
						Log.d(TAG, "CONTACTS_TEL  Not exist this key!");
					}
					list.add(map);
				}

			} else {
				return null;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return list;
	}

	// �洬��ϵ����ϸ��Ϣ
	public HashMap<String, String> getShipContactDetailInfo(String result) {

		HashMap<String, String> map;
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(result);
			String state = null;
			if (jsonObject.has("STATE")) {
				state = jsonObject.getString("STATE");
			}

			if ("1".equalsIgnoreCase(state) && jsonObject.has("SHIP_INFO")) {

				map = new HashMap<String, String>();

				JSONArray jArray = jsonObject.getJSONArray("SHIP_INFO");
				JSONObject object = jArray.getJSONObject(0);

				if (object.has("ID")) {
					map.put("ID", object.getString("ID"));
				} else {
					Log.d(TAG, "ID Not exist this key!");
				}
				if (object.has("SHIP_NAME")) {
					map.put("SHIP_NAME", object.getString("SHIP_NAME"));
				} else {
					Log.d(TAG, "SHIP_NAME Not exist this key!");
				}
				if (object.has("SHIP_NO")) {
					map.put("SHIP_NO", object.getString("SHIP_NO"));
				} else {
					Log.d(TAG, "SHIP_NO Not exist this key!");
				}
				if (object.has("SHIP_LENGTH")) {
					map.put("SHIP_LENGTH", object.getString("SHIP_LENGTH"));
				} else {
					Log.d(TAG, "SHIP_LENGTH Not exist this key!");
				}
				if (object.has("SHIP_WIDTH")) {
					map.put("SHIP_WIDTH", object.getString("SHIP_WIDTH"));
				} else {
					Log.d(TAG, "SHIP_WIDTH  Not exist this key!");
				}
				if (object.has("OWNER_NAME")) {
					map.put("OWNER_NAME", object.getString("OWNER_NAME"));
				} else {
					Log.d(TAG, " OWNER_NAME Not exist this key!");
				}
				if (object.has("OWNER_TEL")) {
					map.put("OWNER_TEL", object.getString("OWNER_TEL"));
				} else {
					Log.d(TAG, "OWNER_TEL Not exist this key!");
				}
				if (object.has("OWNER_NO")) {
					map.put("OWNER_NO", object.getString("OWNER_NO"));
				} else {
					Log.d(TAG, "OWNER_NO  Not exist this key!");
				}
				if (object.has("JOB_TYPE")) {
					map.put("JOB_TYPE", object.getString("JOB_TYPE"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (object.has("JOB_TYPE_2")) {
					map.put("JOB_TYPE_2", object.getString("JOB_TYPE_2"));
				} else {
					Log.d(TAG, " JOB_TYPE_2 Not exist this key!");
				}
				if (object.has("JOB_WAY")) {
					map.put("JOB_WAY", object.getString("JOB_WAY"));
				} else {
					Log.d(TAG, "JOB_WAY  Not exist this key!");
				}
				if (object.has("JOB_WAY_2")) {
					map.put("JOB_WAY_2", object.getString("JOB_WAY_2"));
				} else {
					Log.d(TAG, "JOB_WAY_2 Not exist this key!");
				}
				if (object.has("DICT_MAIN_WORK_CATEGORY")) {
					map.put("DICT_MAIN_WORK_CATEGORY",
							object.getString("DICT_MAIN_WORK_CATEGORY"));
				} else {
					Log.d(TAG, " DICT_MAIN_WORK_CATEGORY Not exist this key!");
				}

			} else {
				return null;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return map;
	}

	public int getSearchRows(String result) {

		JSONObject jsonObject;
		int rows = 0;
		try {
			jsonObject = new JSONObject(result);
			String state = null;
			if (jsonObject.has("STATE")) {
				state = jsonObject.getString("STATE");
			}

			if ("1".equalsIgnoreCase(state) && jsonObject.has("TOTAL")) {

				if (jsonObject.has("TOTAL")) {
					rows = Integer.parseInt(jsonObject.get("TOTAL").toString());
				} else {
					Log.d(TAG, "Not exist this key!");
				}
			} else {
				return -1;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return rows;
	}

	public HashMap<String, String> getShipdetailInfo(String result) {

		HashMap<String, String> map = new HashMap<String, String>();

		JSONObject jsonObject, childJsonObject;

		try {
			jsonObject = new JSONObject(result);
			String state = null;
			if (jsonObject.has("STATE")) {
				state = jsonObject.getString("STATE");
			}

			if ("1".equalsIgnoreCase(state) && jsonObject.has("SHIP_INFO")) {

				childJsonObject = jsonObject.getJSONObject("SHIP_INFO");
				if (childJsonObject.has("SHIP_NAME")) {
					map.put("SHIP_NAME", childJsonObject.getString("SHIP_NAME"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("SHIP_NO")) {
					map.put("SHIP_NO", childJsonObject.getString("SHIP_NO"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("SHIP_DISTRICT")) {
					map.put("SHIP_DISTRICT",
							childJsonObject.getString("SHIP_DISTRICT"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("SHIP_PORT")) {
					map.put("SHIP_PORT", childJsonObject.getString("SHIP_PORT"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("JOB_TYPE")) {
					map.put("JOB_TYPE", childJsonObject.getString("JOB_TYPE"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}

				if (childJsonObject.has("SHIP_BUSINESS_TYPE")) {
					map.put("SHIP_BUSINESS_TYPE",
							childJsonObject.getString("SHIP_BUSINESS_TYPE"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("SHIP_TOT_POWER")) {
					map.put("SHIP_TOT_POWER",
							childJsonObject.getString("SHIP_TOT_POWER"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("APP_SHIP_MATERIAL")) {
					map.put("APP_SHIP_MATERIAL",
							childJsonObject.getString("APP_SHIP_MATERIAL"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("SHIP_LENGTH")) {
					map.put("SHIP_LENGTH",
							childJsonObject.getString("SHIP_LENGTH"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("SHIP_WIDTH")) {
					map.put("SHIP_WIDTH",
							childJsonObject.getString("SHIP_WIDTH"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("SHIP_DEEP")) {
					map.put("SHIP_DEEP", childJsonObject.getString("SHIP_DEEP"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("SHIP_TOT_TON")) {
					map.put("SHIP_TOT_TON",
							childJsonObject.getString("SHIP_TOT_TON"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("SHIP_BUILD_COMP_DATE")) {

					String temp = childJsonObject
							.getString("SHIP_BUILD_COMP_DATE");
					map.put("SHIP_BUILD_COMP_DATE",
							temp.substring(temp.indexOf("(") + 1,
									temp.indexOf("+")));
				} else {
					Log.d(TAG, "Not exist this key!");
				}

				if (childJsonObject.has("JOB_PLACE")) {
					map.put("JOB_PLACE", childJsonObject.getString("JOB_PLACE"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}

				if (childJsonObject.has("OWNER_NAME")) {
					map.put("OWNER_NAME",
							childJsonObject.getString("OWNER_NAME"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("OWNER_NO")) {
					map.put("OWNER_NO", childJsonObject.getString("OWNER_NO"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("OWNER_ADDR")) {
					map.put("OWNER_ADDR",
							childJsonObject.getString("OWNER_ADDR"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("OWNER_TEL")) {
					map.put("OWNER_TEL", childJsonObject.getString("OWNER_TEL"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}

				// OWNER_CERT_NO
				if (childJsonObject.has("OWNER_CERT_NO")) {
					map.put("OWNER_CERT_NO",
							childJsonObject.getString("OWNER_CERT_NO"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				// OWNER_CERT_GET_DATE
				if (childJsonObject.has("OWNER_CERT_GET_DATE")) {
					map.put("OWNER_CERT_GET_DATE",
							childJsonObject.getString("OWNER_CERT_GET_DATE"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				// NATIONALITY_CERT_NO
				if (childJsonObject.has("NATIONALITY_CERT_NO")) {
					map.put("NATIONALITY_CERT_NO",
							childJsonObject.getString("NATIONALITY_CERT_NO"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				// NATIONALITY_CERT_PERIOD_DATE
				if (childJsonObject.has("NATIONALITY_CERT_PERIOD_DATE")) {
					map.put("NATIONALITY_CERT_PERIOD_DATE", childJsonObject
							.getString("NATIONALITY_CERT_PERIOD_DATE"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				// CHECK_CERT_NO
				if (childJsonObject.has("CHECK_CERT_NO")) {
					map.put("CHECK_CERT_NO",
							childJsonObject.getString("CHECK_CERT_NO"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				// CHECK_DEPT
				if (childJsonObject.has("CHECK_DEPT")) {
					map.put("CHECK_DEPT",
							childJsonObject.getString("CHECK_DEPT"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				// CHECK_CERT_DATE
				if (childJsonObject.has("CHECK_CERT_DATE")) {
					map.put("CHECK_CERT_DATE",
							childJsonObject.getString("CHECK_CERT_DATE"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				// CHECK_CERT_PERIOD_DATE
				if (childJsonObject.has("CHECK_CERT_PERIOD_DATE")) {
					map.put("CHECK_CERT_PERIOD_DATE",
							childJsonObject.getString("CHECK_CERT_PERIOD_DATE"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				// FISHING_PERMIT_NO
				if (childJsonObject.has("FISHING_PERMIT_NO")) {
					map.put("FISHING_PERMIT_NO",
							childJsonObject.getString("FISHING_PERMIT_NO"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				// FISHING_PERMIT_CERT_OWNER
				if (childJsonObject.has("FISHING_PERMIT_CERT_OWNER")) {
					map.put("FISHING_PERMIT_CERT_OWNER", childJsonObject
							.getString("FISHING_PERMIT_CERT_OWNER"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				// FISHING_PERMIT_SIGNATE_DEPT
				if (childJsonObject.has("FISHING_PERMIT_SIGNATE_DEPT")) {
					map.put("FISHING_PERMIT_SIGNATE_DEPT", childJsonObject
							.getString("FISHING_PERMIT_SIGNATE_DEPT"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				// FISHING_PERMIT_SIGNATE_TIME
				if (childJsonObject.has("FISHING_PERMIT_SIGNATE_TIME")) {
					map.put("FISHING_PERMIT_SIGNATE_TIME", childJsonObject
							.getString("FISHING_PERMIT_SIGNATE_TIME"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				// FISHING_PERMIT_LOGOUT_TIME
				if (childJsonObject.has("FISHING_PERMIT_LOGOUT_TIME")) {
					map.put("FISHING_PERMIT_LOGOUT_TIME", childJsonObject
							.getString("FISHING_PERMIT_LOGOUT_TIME"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				// FISHING_GEAR_NAME
				if (childJsonObject.has("FISHING_GEAR_NAME")) {
					map.put("FISHING_GEAR_NAME",
							childJsonObject.getString("FISHING_GEAR_NAME"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}

				if (childJsonObject.has("JOB_PLACE")) {
					map.put("JOB_PLACE", childJsonObject.getString("JOB_PLACE"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}

				if (childJsonObject.has("OWNER_NAME")) {
					map.put("OWNER_NAME",
							childJsonObject.getString("OWNER_NAME"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("OWNER_NO")) {
					map.put("OWNER_NO", childJsonObject.getString("OWNER_NO"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("OWNER_ADDR")) {
					map.put("OWNER_ADDR",
							childJsonObject.getString("OWNER_ADDR"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("OWNER_TEL")) {
					map.put("OWNER_TEL", childJsonObject.getString("OWNER_TEL"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}

			} else {
				return null;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	public ArrayList<HashMap<String, String>> getShipGPSLL(String result) {

		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		String state = null;
		try {
			JSONObject jsonObject = new JSONObject(result);
			if (jsonObject.has("STATE")) {
				state = jsonObject.getString("STATE");
			} else {
				Log.d(TAG, "The input data has not contain \"STATE\" key");
			}

			JSONArray AISJsonArray = null, BDJsonArray = null;
			if ("1".equalsIgnoreCase(state)) {
				if (jsonObject.has("SHIP_AIS")) {
					AISJsonArray = jsonObject.getJSONArray("SHIP_AIS");
				} else {
					Log.d(TAG,
							"The input data has not contain \"SHIP_AIS\" key");
				}
				if (jsonObject.has("SHIP_BD")) {
					BDJsonArray = jsonObject.getJSONArray("SHIP_BD");
				} else {
					Log.d(TAG, "The input data has not contain \"SHIP_BD\" key");
				}
				for (int i = 0; i < AISJsonArray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject childJsonObject = AISJsonArray.getJSONObject(i);
					if (childJsonObject.has("name")) {
						map.put("name", childJsonObject.getString("name"));
					} else {
						Log.d(TAG,
								"The input data has not contain \"name\" key");
					}
					if (childJsonObject.has("latitude")) {
						map.put("latitude",
								childJsonObject.getString("latitude"));
					} else {
						Log.d(TAG,
								"The input data has not contain \"latitude\" key");
					}
					if (childJsonObject.has("longtude")) {
						map.put("longtude",
								childJsonObject.getString("longtude"));
					} else {
						Log.d(TAG,
								"The input data has not contain \"longtude\" key");
					}

					map.put("gpstype", "ais");
					list.add(map);
				}

				for (int i = 0; i < BDJsonArray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject childJsonObject = BDJsonArray.getJSONObject(i);
					if (childJsonObject.has("name")) {
						map.put("name", childJsonObject.getString("name"));
					} else {
						Log.d(TAG,
								"The input data has not contain \"name 1\" key");
					}
					if (childJsonObject.has("drlatitude")) {
						map.put("latitude",
								childJsonObject.getString("drlatitude"));
					} else {
						Log.d(TAG,
								"The input data has not contain \"drlatitude 1\" key");
					}
					if (childJsonObject.has("drlongtude")) {
						map.put("longtude",
								childJsonObject.getString("drlongtude"));
					} else {
						Log.d(TAG,
								"The input data has not contain \"drlongtude 2\" key");
					}

					map.put("gpstype", "bd");
					list.add(map);
				}

			} else {
				return null;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList<HashMap<String, String>> getBoatTimeLine(String result) {

		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		String state = null;
		try {
			JSONObject jsonObject = new JSONObject(result);
			if (jsonObject.has("state")) {
				state = jsonObject.getString("state");
			} else {
				Log.d(TAG, "The input data has not contain \"STATE\" key");
			}

			JSONArray timeLineJsonArray = null;
			if ("1".equalsIgnoreCase(state)) {
				if (jsonObject.has("timeLine")) {
					timeLineJsonArray = jsonObject.getJSONArray("timeLine");
				} else {
					Log.d(TAG,
							"The input data has not contain \"timeLine\" key");
				}
				for (int i = 0; i < timeLineJsonArray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject childJsonObject = timeLineJsonArray
							.getJSONObject(i);
					String FunName = null;
					String date = null;
					if (childJsonObject.has("FunName")) {
						FunName = childJsonObject.getString("FunName");
						// map.put("FunName", FunName);
					} else {
						Log.d(TAG,
								"The input data has not contain \"FunName\" key");
					}
					// ����Ϣ
					if ("BoatBaseInfo".equals(FunName)) {
						// �������ʱ��
						if (childJsonObject.has("ShipBuildCompDate")) {
							date = childJsonObject
									.getString("ShipBuildCompDate");
							if (date.contains("Date")) {
								date = getDateFromTime(date.substring(
										date.indexOf("(") + 1,
										date.indexOf("+")));
							}
							map.put("date", date);
							map.put("FunName", "�洬�������");
						} else {
							Log.d(TAG,
									"The input data has not contain \"ShipBuildCompDate\" key");
						}
					} else if ("BoatNameInfo".equals(FunName))// ����Ǽ���Ϣ
					{
						// ǩ��ʱ��
						if (childJsonObject.has("Issue_date")) {
							date = childJsonObject.getString("Issue_date");
							if (date.contains("Date")) {
								date = getDateFromTime(date.substring(
										date.indexOf("(") + 1,
										date.indexOf("+")));
							}
							map.put("date", date);
							map.put("FunName", "�洬�Ǽ�");
						} else {
							Log.d(TAG,
									"The input data has not contain \"Issue_date\" key");
						}
					} else if ("BoatCheckDataInfo".equals(FunName))// ������Ϣ
					{
						// �����������
						if (childJsonObject.has("MainVerifyEndTime")) {
							date = childJsonObject
									.getString("MainVerifyEndTime");
							if (date.contains("Date")) {
								date = getDateFromTime(date.substring(
										date.indexOf("(") + 1,
										date.indexOf("+")));
							}
							map.put("date", date);
							map.put("FunName", "�洬����");
						} else {
							Log.d(TAG,
									"The input data has not contain \"MainVerifyEndTime\" key");
						}
					} else if ("BoatNetInfo".equals(FunName))// ������Ϣ
					{
						// ǩ������
						if (childJsonObject.has("Signate_time")) {
							date = childJsonObject.getString("Signate_time");
							if (date.contains("Date")) {
								date = getDateFromTime(date.substring(
										date.indexOf("(") + 1,
										date.indexOf("+")));
							}
							map.put("date", date);
							map.put("FunName", "��ȡ�������֤");
						} else {
							Log.d(TAG,
									"The input data has not contain \"Signate_time\" key");
						}
					} else if ("BoatPowerInfo".equals(FunName))// ����Ȩ��Ϣ
					{
						// ȡ������Ȩ����
						if (childJsonObject.has("Ownership_get_date")) {
							date = childJsonObject
									.getString("Ownership_get_date");
							if (date.contains("Date")) {
								date = getDateFromTime(date.substring(
										date.indexOf("(") + 1,
										date.indexOf("+")));
							}
							map.put("date", date);
							map.put("FunName", "�洬��ȡ����Ȩ");
						} else {
							Log.d(TAG,
									"The input data has not contain \"Ownership_get_date\" key");
						}
					} else if ("BoatOilInfo".equals(FunName))// �Ͳ���Ϣ
					{
						// �Ͳ�����
						if (childJsonObject.has("Oil_time")) {
							date = childJsonObject.getString("Oil_time");
							if (date.contains("Date")) {
								date = getDateFromTime(date.substring(
										date.indexOf("(") + 1,
										date.indexOf("+")));
							}
							map.put("date", date);
							map.put("FunName", "�����Ͳ�");
						} else {
							Log.d(TAG,
									"The input data has not contain \"Oil_time\" key");
						}
					} else if ("BoatCaseInfo".equals(FunName))// ����
					{
						// �ܰ�ʱ��
						if (childJsonObject.has("Date")) {
							date = childJsonObject.getString("Date");
							if (date.contains("Date")) {
								date = getDateFromTime(date.substring(
										date.indexOf("(") + 1,
										date.indexOf("+")));
							}
							map.put("date", date);
							map.put("FunName", "�洬��������");
						} else {
							Log.d(TAG,
									"The input data has not contain \"Date\" key");
						}
					} else if ("BoatHBInfo".equals(FunName))// ����
					{
						// �˱�����ʱ��
						if (childJsonObject.has("T_udr_date")) {
							date = childJsonObject.getString("T_udr_date");
							if (date.contains("Date")) {
								date = getDateFromTime(date.substring(
										date.indexOf("(") + 1,
										date.indexOf("+")));
							}
							map.put("date", date);
							map.put("FunName", "�洬���պ˲�");
						} else {
							Log.d(TAG,
									"The input data has not contain \"T_udr_date\" key");
						}
					} else if ("BoatPAInfo".equals(FunName))// �ⰸ��Ϣ
					{
						// ��������
						if (childJsonObject.has("T_cls_tm")) {
							date = childJsonObject.getString("T_cls_tm");
							if (date.contains("Date")) {
								date = getDateFromTime(date.substring(
										date.indexOf("(") + 1,
										date.indexOf("+")));
							}
							map.put("date", date);
							map.put("FunName", "�洬����");
						} else {
							Log.d(TAG,
									"The input data has not contain \"T_cls_tm\" key");
						}
					} else if ("BoatJYInfo".equals(FunName))// ������Ϣ
					{
						// �춨����
						if (childJsonObject.has("T_signed_date")) {
							date = childJsonObject.getString("T_signed_date");
							if (date.contains("Date")) {
								date = getDateFromTime(date.substring(
										date.indexOf("(") + 1,
										date.indexOf("+")));
							}
							map.put("date", date);
							map.put("FunName", "�洬����");
						} else {
							Log.d(TAG,
									"The input data has not contain \"T_signed_date\" key");
						}
					}

					list.add(map);
				}

			} else {
				return null;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	@SuppressLint("UseValueOf")
	public String getDateFromTime(String t) {

		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(t);
		Long time = 0L;
		java.util.Date date = null;
		time = new Long(m.replaceAll("").trim());
		if (time < 10000000000L) {
			time = time * 1000;
		}
		return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(time);
	}

	public ArrayList<HashMap<String, String>> getbasicShipOilPatchInfo(
			String result) {

		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

		JSONObject jsonObject;
		JSONArray jsonArray;
		try {
			jsonObject = new JSONObject(result);
			String state = null;
			if (jsonObject.has("STATE")) {
				state = jsonObject.getString("STATE");
			}

			if ("1".equalsIgnoreCase(state) && jsonObject.has("SHIP_INFO")) {

				jsonArray = jsonObject.getJSONArray("SHIP_INFO");
				for (int i = 0; i < jsonArray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject object = jsonArray.getJSONObject(i);
					if (object.has("Ship_name")) {
						map.put("Ship_name", object.getString("Ship_name"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("Ship_no")) {
						map.put("Ship_no", object.getString("Ship_no"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("Ship_port")) {
						map.put("Ship_port", object.getString("Ship_port"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("Ship_tot_ton")) {
						map.put("Ship_tot_ton",
								object.getString("Ship_tot_ton"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("Ship_tot_power")) {
						map.put("Ship_tot_power",
								object.getString("Ship_tot_power"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("Ship_age")) {
						map.put("Ship_age", object.getString("Ship_age"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("Funds_thisYear")) {
						map.put("Funds_thisYear",
								object.getString("Funds_thisYear"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					list.add(map);
				}

			} else {
				return null;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public HashMap<String, String> getShipOilPatchdetailInfo(String result) {

		HashMap<String, String> map = new HashMap<String, String>();

		JSONObject jsonObject, childJsonObject;

		try {
			jsonObject = new JSONObject(result);
			String state = null;
			if (jsonObject.has("STATE")) {
				state = jsonObject.getString("STATE");
			}

			if ("1".equalsIgnoreCase(state) && jsonObject.has("SHIP_INFO")) {

				childJsonObject = jsonObject.getJSONObject("SHIP_INFO");
				if (childJsonObject.has("Ship_name")) {
					map.put("Ship_name", childJsonObject.getString("Ship_name"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("Ship_no")) {
					map.put("Ship_no", childJsonObject.getString("Ship_no"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("Ship_port")) {
					map.put("Ship_port", childJsonObject.getString("Ship_port"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("Dict_job_type")) {
					map.put("Dict_job_type",
							childJsonObject.getString("Dict_job_type"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("Ship_length")) {
					map.put("Ship_length",
							childJsonObject.getString("Ship_length"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("Ship_tot_ton")) {
					map.put("Ship_tot_ton",
							childJsonObject.getString("Ship_tot_ton"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("Ship_build_comp_date")) {
					map.put("Ship_build_comp_date",
							childJsonObject.getString("Ship_build_comp_date"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("Ship_type_ch")) {
					map.put("Ship_type_ch",
							childJsonObject.getString("Ship_type_ch"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("Ship_tot_power")) {
					map.put("Ship_tot_power",
							childJsonObject.getString("Ship_tot_power"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("Main_certificate_number")) {
					map.put("Main_certificate_number", childJsonObject
							.getString("Main_certificate_number"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("Ship_register_number")) {
					map.put("Ship_register_number",
							childJsonObject.getString("Ship_register_number"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("Fishing_permit_number")) {
					map.put("Fishing_permit_number",
							childJsonObject.getString("Fishing_permit_number"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}

				if (childJsonObject.has("Owner_name")) {
					map.put("Owner_name",
							childJsonObject.getString("Owner_name"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}

				if (childJsonObject.has("Owner_no")) {
					map.put("Owner_no", childJsonObject.getString("Owner_no"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("Owner_addr")) {
					map.put("Owner_addr",
							childJsonObject.getString("Owner_addr"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("Owner_city")) {
					map.put("Owner_city",
							childJsonObject.getString("Owner_city"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("Owner_county")) {
					map.put("Owner_county",
							childJsonObject.getString("Owner_county"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}

				// OWNER_CERT_NO
				if (childJsonObject.has("Owner_town")) {
					map.put("Owner_town",
							childJsonObject.getString("Owner_town"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				// OWNER_CERT_GET_DATE
				if (childJsonObject.has("Owner_village")) {
					map.put("Owner_village",
							childJsonObject.getString("Owner_village"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				// NATIONALITY_CERT_NO
				if (childJsonObject.has("Owner_specific_addr")) {
					map.put("Owner_specific_addr",
							childJsonObject.getString("Owner_specific_addr"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				// NATIONALITY_CERT_PERIOD_DATE
				if (childJsonObject.has("Owner_account_name")) {
					map.put("Owner_account_name",
							childJsonObject.getString("Owner_account_name"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				// CHECK_CERT_NO
				if (childJsonObject.has("Owner_bank")) {
					map.put("Owner_bank",
							childJsonObject.getString("Owner_bank"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				// CHECK_DEPT
				if (childJsonObject.has("Owner_account")) {
					map.put("Owner_account",
							childJsonObject.getString("Owner_account"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				// CHECK_CERT_DATE
				if (childJsonObject.has("Owner_tel")) {
					map.put("Owner_tel", childJsonObject.getString("Owner_tel"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				// CHECK_CERT_PERIOD_DATE
				if (childJsonObject.has("Produce")) {
					map.put("Produce", childJsonObject.getString("Produce"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				// FISHING_PERMIT_NO
				if (childJsonObject.has("Out_of_line")) {
					map.put("Out_of_line",
							childJsonObject.getString("Out_of_line"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				// FISHING_PERMIT_CERT_OWNER
				if (childJsonObject.has("Proportion")) {
					map.put("Proportion",
							childJsonObject.getString("Proportion"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				// FISHING_PERMIT_SIGNATE_DEPT
				if (childJsonObject.has("Fuel_consumption")) {
					map.put("Fuel_consumption",
							childJsonObject.getString("Fuel_consumption"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				// FISHING_PERMIT_SIGNATE_TIME
				if (childJsonObject.has("Remark")) {
					map.put("Remark", childJsonObject.getString("Remark"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				// FISHING_PERMIT_LOGOUT_TIME
				if (childJsonObject.has("Dist_ship_district")) {
					map.put("Dist_ship_district",
							childJsonObject.getString("Dist_ship_district"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				// FISHING_GEAR_NAME
				if (childJsonObject.has("City")) {
					map.put("City", childJsonObject.getString("City"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}

				if (childJsonObject.has("County")) {
					map.put("County", childJsonObject.getString("County"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}

				if (childJsonObject.has("Town")) {
					map.put("Town", childJsonObject.getString("Town"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("Village")) {
					map.put("Village", childJsonObject.getString("Village"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("Specific_addr")) {
					map.put("Specific_addr",
							childJsonObject.getString("Specific_addr"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("Administrative")) {
					map.put("Administrative",
							childJsonObject.getString("Administrative"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("Job_way")) {
					map.put("Job_way", childJsonObject.getString("Job_way"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}

				if (childJsonObject.has("Ship_age")) {
					map.put("Ship_age", childJsonObject.getString("Ship_age"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("Oil_time")) {
					map.put("Oil_time", childJsonObject.getString("Oil_time"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("Funds_2014")) {
					map.put("Funds_2014",
							childJsonObject.getString("Funds_2014"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("Funds_2015")) {
					map.put("Funds_2015",
							childJsonObject.getString("Funds_2015"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("Funds_2016")) {
					map.put("Funds_2016",
							childJsonObject.getString("Funds_2016"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}

				if (childJsonObject.has("Funds_2017")) {
					map.put("Funds_2017",
							childJsonObject.getString("Funds_2017"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("Funds_2018")) {
					map.put("Funds_2018",
							childJsonObject.getString("Funds_2018"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("Funds_2019")) {
					map.put("Funds_2019",
							childJsonObject.getString("Funds_2019"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("Funds_thisYear")) {
					map.put("Funds_thisYear",
							childJsonObject.getString("Funds_thisYear"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}

			} else {
				return null;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	public ArrayList<HashMap<String, String>> getbasicDetectShipInfo(
			String result) {

		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

		JSONObject jsonObject;
		JSONArray jsonArray;
		try {
			jsonObject = new JSONObject(result);
			String state = null;
			if (jsonObject.has("STATE")) {
				state = jsonObject.getString("STATE");
			}

			if ("1".equalsIgnoreCase(state) && jsonObject.has("SHIP_INFO")) {

				jsonArray = jsonObject.getJSONArray("SHIP_INFO");
				for (int i = 0; i < jsonArray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject object = jsonArray.getJSONObject(i);
					if (object.has("ship_Id")) {
						map.put("ship_Id", object.getString("ship_Id"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("ship_Name")) {
						map.put("ship_Name", object.getString("ship_Name"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("ship_No")) {
						map.put("ship_No", object.getString("ship_No"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("ship_Owner")) {
						map.put("ship_Owner", object.getString("ship_Owner"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("verifyenr_Type")) {
						map.put("verifyenr_Type",
								object.getString("verifyenr_Type"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("certificate_Number")) {
						map.put("certificate_Number",
								object.getString("certificate_Number"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("build_Compdate")) {
						map.put("build_Compdate",
								object.getString("build_Compdate"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("verify_End_Time")) {
						map.put("verify_End_Time",
								object.getString("verify_End_Time"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("next_Verify_Time")) {
						map.put("next_Verify_Time",
								object.getString("next_Verify_Time"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("verify_Dept")) {
						map.put("verify_Dept", object.getString("verify_Dept"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}

					list.add(map);
				}

			} else {
				return null;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public HashMap<String, String> getDetectShipdetailInfo(String result) {

		HashMap<String, String> map = new HashMap<String, String>();

		JSONObject jsonObject, childJsonObject;
		JSONArray jsonArray;

		try {
			jsonObject = new JSONObject(result);
			String state = null;
			if (jsonObject.has("STATE")) {
				state = jsonObject.getString("STATE");
			}

			if ("1".equalsIgnoreCase(state) && jsonObject.has("SHIP_INFO")) {

				jsonArray = jsonObject.getJSONArray("SHIP_INFO");
				childJsonObject = jsonArray.getJSONObject(0);
				if (childJsonObject.has("ID")) {
					map.put("ID", childJsonObject.getString("ID"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("sheet_id")) {
					map.put("sheet_id", childJsonObject.getString("sheet_id"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("dict_main_work_category")) {
					map.put("dict_main_work_category", childJsonObject
							.getString("dict_main_work_category"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("link_ship_name_ch")) {
					map.put("link_ship_name_ch",
							childJsonObject.getString("link_ship_name_ch"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}

				if (childJsonObject.has("link_ship_owner_ch")) {
					map.put("link_ship_owner_ch",
							childJsonObject.getString("link_ship_owner_ch"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("link_ship_owner_address")) {
					map.put("link_ship_owner_address", childJsonObject
							.getString("link_ship_owner_address"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("link_verifyenr_no")) {
					map.put("link_verifyenr_no",
							childJsonObject.getString("link_verifyenr_no"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("link_ship_coding")) {
					map.put("link_ship_coding",
							childJsonObject.getString("link_ship_coding"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("link_build_compdate")) {
					map.put("link_build_compdate",
							childJsonObject.getString("link_build_compdate"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("dict_main_norm_material")) {
					map.put("dict_main_norm_material", childJsonObject
							.getString("dict_main_norm_material"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("dict_main_verifyenr_type")) {
					map.put("dict_main_verifyenr_type", childJsonObject
							.getString("dict_main_verifyenr_type"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("dict_main_verifyenr_warrant")) {
					map.put("dict_main_verifyenr_warrant", childJsonObject
							.getString("dict_main_verifyenr_warrant"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("dist_main_ship_belong_district")) {
					map.put("dist_main_ship_belong_district", childJsonObject
							.getString("dist_main_ship_belong_district"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("main_Work_Area")) {
					map.put("main_Work_Area",
							childJsonObject.getString("main_Work_Area"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("main_norm_num_power")) {
					map.put("main_norm_num_power",
							childJsonObject.getString("main_norm_num_power"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("main_norm_captain")) {
					map.put("main_norm_captain",
							childJsonObject.getString("main_norm_captain"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("main_norm_total_tonnage")) {
					map.put("main_norm_total_tonnage", childJsonObject
							.getString("main_norm_total_tonnage"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("dict_link_ship_type_ch")) {
					map.put("dict_link_ship_type_ch",
							childJsonObject.getString("dict_link_ship_type_ch"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("link_pactship_long")) {
					map.put("link_pactship_long",
							childJsonObject.getString("link_pactship_long"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("link_total_lenght")) {
					map.put("link_total_lenght",
							childJsonObject.getString("link_total_lenght"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("link_ship_wide")) {
					map.put("link_ship_wide",
							childJsonObject.getString("link_ship_wide"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("link_Ship_Deep")) {
					map.put("link_Ship_Deep",
							childJsonObject.getString("link_Ship_Deep"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("NATIONALITY_CERT_PERIOD_DATE")) {
					map.put("NATIONALITY_CERT_PERIOD_DATE", childJsonObject
							.getString("NATIONALITY_CERT_PERIOD_DATE"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("link_total_tonnage")) {
					map.put("link_total_tonnage",
							childJsonObject.getString("link_total_tonnage"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("link_net_tonnage")) {
					map.put("link_net_tonnage",
							childJsonObject.getString("link_net_tonnage"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("link_ship_type_code")) {
					map.put("link_ship_type_code",
							childJsonObject.getString("link_ship_type_code"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("link_design_speed")) {
					map.put("link_design_speed",
							childJsonObject.getString("link_design_speed"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("link_rated_load_num")) {
					map.put("link_rated_load_num",
							childJsonObject.getString("link_rated_load_num"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("link_total_power")) {
					map.put("link_total_power",
							childJsonObject.getString("link_total_power"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("link_put_keel_time")) {
					map.put("link_put_keel_time",
							childJsonObject.getString("link_put_keel_time"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("link_fishing_factory_ch")) {
					map.put("link_fishing_factory_ch", childJsonObject
							.getString("link_fishing_factory_ch"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("link_ship_operating_ch")) {
					map.put("link_ship_operating_ch",
							childJsonObject.getString("link_ship_operating_ch"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("main_Verify_Start_Time")) {
					map.put("main_Verify_Start_Time",
							childJsonObject.getString("main_Verify_Start_Time"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("main_verify_end_time")) {
					map.put("main_verify_end_time",
							childJsonObject.getString("main_verify_end_time"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("main_next_verify_time")) {
					map.put("main_next_verify_time",
							childJsonObject.getString("main_next_verify_time"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("main_verify_dept_id")) {
					map.put("main_verify_dept_id",
							childJsonObject.getString("main_verify_dept_id"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("main_certificate_validity")) {
					map.put("main_certificate_validity", childJsonObject
							.getString("main_certificate_validity"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("main_exhibition_period_validity")) {
					map.put("main_exhibition_period_validity", childJsonObject
							.getString("main_exhibition_period_validity"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("main_last_visa_validity_term")) {
					map.put("main_last_visa_validity_term", childJsonObject
							.getString("main_last_visa_validity_term"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("main_verifyenr_address_ch")) {
					map.put("main_verifyenr_address_ch", childJsonObject
							.getString("main_verifyenr_address_ch"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("main_address_ch")) {
					map.put("main_address_ch",
							childJsonObject.getString("main_address_ch"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("dict_main_Next_Verify_Type")) {
					map.put("dict_main_Next_Verify_Type", childJsonObject
							.getString("dict_main_Next_Verify_Type"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("main_ceritificate_date")) {
					map.put("main_ceritificate_date",
							childJsonObject.getString("main_ceritificate_date"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("main_certificate_number")) {
					map.put("main_certificate_number", childJsonObject
							.getString("main_certificate_number"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("log_vessel")) {
					map.put("log_vessel",
							childJsonObject.getString("log_vessel"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("CREATE_USER")) {
					map.put("CREATE_USER",
							childJsonObject.getString("CREATE_USER"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("CREATE_DATE")) {
					map.put("CREATE_DATE",
							childJsonObject.getString("CREATE_DATE"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("UPDATE_USER")) {
					map.put("UPDATE_USER",
							childJsonObject.getString("UPDATE_USER"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("UPDATE_DATE")) {
					map.put("UPDATE_DATE",
							childJsonObject.getString("UPDATE_DATE"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}

			} else {
				return null;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	public ArrayList<HashMap<String, String>> getbasicCrewInfo(String result) {

		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

		JSONObject jsonObject;
		JSONArray jsonArray;
		try {
			jsonObject = new JSONObject(result);
			String state = null;
			if (jsonObject.has("STATE")) {
				state = jsonObject.getString("STATE");
			}

			if ("1".equalsIgnoreCase(state) && jsonObject.has("SHIP_INFO")) {

				jsonArray = jsonObject.getJSONArray("SHIP_INFO");
				for (int i = 0; i < jsonArray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject object = jsonArray.getJSONObject(i);
					if (object.has("id")) {
						map.put("id", object.getString("id"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("name")) {
						map.put("name", object.getString("name"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("idCard")) {
						map.put("idCard", object.getString("idCard"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("sex")) {
						map.put("sex", object.getString("sex"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("born")) {
						map.put("born", object.getString("born"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("crew_job")) {
						map.put("crew_job", object.getString("crew_job"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("report_city")) {
						map.put("report_city", object.getString("report_city"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("report_county")) {
						map.put("report_county",
								object.getString("report_county"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("cert_Number")) {
						map.put("cert_Number", object.getString("cert_Number"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("issue_Date")) {
						map.put("issue_Date", object.getString("issue_Date"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}

					list.add(map);
				}

			} else {
				return null;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public HashMap<String, String> getdetailCrewInfo(String result) {

		HashMap<String, String> map = new HashMap<String, String>();

		JSONObject jsonObject, childJsonObject;
		JSONArray jsonArray;

		try {
			jsonObject = new JSONObject(result);
			String state = null;
			if (jsonObject.has("STATE")) {
				state = jsonObject.getString("STATE");
			}

			if ("1".equalsIgnoreCase(state) && jsonObject.has("SHIP_INFO")) {

				jsonArray = jsonObject.getJSONArray("SHIP_INFO");
				childJsonObject = jsonArray.getJSONObject(0);
				if (childJsonObject.has("id")) {
					map.put("id", childJsonObject.getString("id"));
				} else {
					Log.d(TAG, "Not exist this key id!");
				}
				if (childJsonObject.has("ID_number")) {
					map.put("ID_number", childJsonObject.getString("ID_number"));
				} else {
					Log.d(TAG, "Not exist this key ID_number!");
				}
				if (childJsonObject.has("category")) {
					map.put("category", childJsonObject.getString("category"));
				} else {
					Log.d(TAG, "Not exist this key category!");
				}
				if (childJsonObject.has("name")) {
					map.put("name", childJsonObject.getString("name"));
				} else {
					Log.d(TAG, "Not exist this key name!");
				}

				if (childJsonObject.has("sex")) {
					map.put("sex", childJsonObject.getString("sex"));
				} else {
					Log.d(TAG, "Not exist this key sex!");
				}
				if (childJsonObject.has("nation")) {
					map.put("nation", childJsonObject.getString("nation"));
				} else {
					Log.d(TAG, "Not exist this key nation!");
				}
				if (childJsonObject.has("born")) {
					map.put("born", childJsonObject.getString("born"));
				} else {
					Log.d(TAG, "Not exist this key born!");
				}
				if (childJsonObject.has("ID_address")) {
					map.put("ID_address",
							childJsonObject.getString("ID_address"));
				} else {
					Log.d(TAG, "Not exist this key ID_address!");
				}
				if (childJsonObject.has("ID_issue")) {
					map.put("ID_issue", childJsonObject.getString("ID_issue"));
				} else {
					Log.d(TAG, "Not exist this key ID_issue!");
				}
				if (childJsonObject.has("ID_start")) {
					map.put("ID_start", childJsonObject.getString("ID_start"));
				} else {
					Log.d(TAG, "Not exist this key ID_start!");
				}
				if (childJsonObject.has("ID_end")) {
					map.put("ID_end", childJsonObject.getString("ID_end"));
				} else {
					Log.d(TAG, "Not exist this key ID_end!");
				}
				if (childJsonObject.has("nationality")) {
					map.put("nationality",
							childJsonObject.getString("nationality"));
				} else {
					Log.d(TAG, "Not exist this key nationality!");
				}
				if (childJsonObject.has("unit_code")) {
					map.put("unit_code", childJsonObject.getString("unit_code"));
				} else {
					Log.d(TAG, "Not exist this key unit_code!");
				}
				if (childJsonObject.has("job_title")) {
					map.put("job_title", childJsonObject.getString("job_title"));
				} else {
					Log.d(TAG, "Not exist this key job_title!");
				}
				if (childJsonObject.has("institution")) {
					map.put("institution",
							childJsonObject.getString("institution"));
				} else {
					Log.d(TAG, "Not exist this key institution!");
				}
				if (childJsonObject.has("major")) {
					map.put("major", childJsonObject.getString("major"));
				} else {
					Log.d(TAG, "Not exist this key major!");
				}
				if (childJsonObject.has("graduation_date")) {
					map.put("graduation_date",
							childJsonObject.getString("graduation_date"));
				} else {
					Log.d(TAG, "Not exist this key graduation_date!");
				}
				if (childJsonObject.has("graduation_number")) {
					map.put("graduation_number",
							childJsonObject.getString("graduation_number"));
				} else {
					Log.d(TAG, "Not exist this key graduation_number!");
				}
				if (childJsonObject.has("educational_background")) {
					map.put("educational_background",
							childJsonObject.getString("educational_background"));
				} else {
					Log.d(TAG, "Not exist this key educational_background!");
				}
				if (childJsonObject.has("degree")) {
					map.put("degree", childJsonObject.getString("degree"));
				} else {
					Log.d(TAG, "Not exist this key degree!");
				}
				if (childJsonObject.has("census_province")) {
					map.put("census_province",
							childJsonObject.getString("census_province"));
				} else {
					Log.d(TAG, "Not exist this key census_province!");
				}
				if (childJsonObject.has("census_city")) {
					map.put("census_city",
							childJsonObject.getString("census_city"));
				} else {
					Log.d(TAG, "Not exist this key census_city!");
				}
				if (childJsonObject.has("census_county")) {
					map.put("census_county",
							childJsonObject.getString("census_county"));
				} else {
					Log.d(TAG, "Not exist this key census_county!");
				}
				if (childJsonObject.has("census_address")) {
					map.put("census_address",
							childJsonObject.getString("census_address"));
				} else {
					Log.d(TAG, "Not exist this key! census_address");
				}
				if (childJsonObject.has("comm_province")) {
					map.put("comm_province",
							childJsonObject.getString("comm_province"));
				} else {
					Log.d(TAG, "Not exist this key! comm_province");
				}
				if (childJsonObject.has("comm_city")) {
					map.put("comm_city", childJsonObject.getString("comm_city"));
				} else {
					Log.d(TAG, "Not exist this key! comm_city");
				}
				if (childJsonObject.has("comm_county")) {
					map.put("comm_county",
							childJsonObject.getString("comm_county"));
				} else {
					Log.d(TAG, "Not exist this key! comm_county");
				}
				if (childJsonObject.has("comm_address")) {
					map.put("comm_address",
							childJsonObject.getString("comm_address"));
				} else {
					Log.d(TAG, "Not exist this key! comm_address");
				}
				if (childJsonObject.has("fixed_tel")) {
					map.put("fixed_tel", childJsonObject.getString("fixed_tel"));
				} else {
					Log.d(TAG, "Not exist this key! fixed_tel");
				}
				if (childJsonObject.has("other_tel")) {
					map.put("other_tel", childJsonObject.getString("other_tel"));
				} else {
					Log.d(TAG, "Not exist this key! other_tel");
				}
				if (childJsonObject.has("tel")) {
					map.put("tel", childJsonObject.getString("tel"));
				} else {
					Log.d(TAG, "Not exist this key! tel");
				}
				if (childJsonObject.has("zip_code")) {
					map.put("zip_code", childJsonObject.getString("zip_code"));
				} else {
					Log.d(TAG, "Not exist this key! zip_code");
				}
				if (childJsonObject.has("email")) {
					map.put("email", childJsonObject.getString("email"));
				} else {
					Log.d(TAG, "Not exist this key! email");
				}
				if (childJsonObject.has("source")) {
					map.put("source", childJsonObject.getString("source"));
				} else {
					Log.d(TAG, "Not exist this key! source");
				}
				if (childJsonObject.has("created")) {
					map.put("created", childJsonObject.getString("created"));
				} else {
					Log.d(TAG, "Not exist this key! created");
				}
				if (childJsonObject.has("created_name")) {
					map.put("created_name",
							childJsonObject.getString("created_name"));
				} else {
					Log.d(TAG, "Not exist this key! created_name");
				}
				if (childJsonObject.has("created_agencies")) {
					map.put("created_agencies",
							childJsonObject.getString("created_agencies"));
				} else {
					Log.d(TAG, "Not exist this key! created_agencies");
				}
				if (childJsonObject.has("created_time")) {
					map.put("created_time",
							childJsonObject.getString("created_time"));
				} else {
					Log.d(TAG, "Not exist this key! created_time");
				}
				if (childJsonObject.has("operator_agencies")) {
					map.put("operator_agencies",
							childJsonObject.getString("operator_agencies"));
				} else {
					Log.d(TAG, "Not exist this key! operator_agencies");
				}
				if (childJsonObject.has("operator")) {
					map.put("operator", childJsonObject.getString("operator"));
				} else {
					Log.d(TAG, "Not exist this key! operator");
				}
				if (childJsonObject.has("operator_name")) {
					map.put("operator_name",
							childJsonObject.getString("operator_name"));
				} else {
					Log.d(TAG, "Not exist this key! operator_name");
				}
				if (childJsonObject.has("operator_time")) {
					map.put("operator_time",
							childJsonObject.getString("operator_time"));
				} else {
					Log.d(TAG, "Not exist this key! operator_time");
				}
				if (childJsonObject.has("flag")) {
					map.put("flag", childJsonObject.getString("flag"));
				} else {
					Log.d(TAG, "Not exist this key! flag");
				}
				if (childJsonObject.has("certificate_number")) {
					map.put("certificate_number",
							childJsonObject.getString("certificate_number"));
				} else {
					Log.d(TAG, "Not exist this key! certificate_number");
				}
				if (childJsonObject.has("issue_year")) {
					map.put("issue_year",
							childJsonObject.getString("issue_year"));
				} else {
					Log.d(TAG, "Not exist this key! issue_year");
				}
				if (childJsonObject.has("issue_month")) {
					map.put("issue_month",
							childJsonObject.getString("issue_month"));
				} else {
					Log.d(TAG, "Not exist this key! issue_month");
				}
				if (childJsonObject.has("remark")) {
					map.put("remark", childJsonObject.getString("remark"));
				} else {
					Log.d(TAG, "Not exist this key! remark");
				}
				if (childJsonObject.has("unit")) {
					map.put("unit", childJsonObject.getString("unit"));
				} else {
					Log.d(TAG, "Not exist this key! unit");
				}
				if (childJsonObject.has("crew_rank")) {
					map.put("crew_rank", childJsonObject.getString("crew_rank"));
				} else {
					Log.d(TAG, "Not exist this key! crew_rank");
				}
				if (childJsonObject.has("navigation_area")) {
					map.put("navigation_area",
							childJsonObject.getString("navigation_area"));
				} else {
					Log.d(TAG, "Not exist this key! navigation_area");
				}
				if (childJsonObject.has("ship_type")) {
					map.put("ship_type", childJsonObject.getString("ship_type"));
				} else {
					Log.d(TAG, "Not exist this key! ship_type");
				}
				if (childJsonObject.has("cancellation_date")) {
					map.put("cancellation_date",
							childJsonObject.getString("cancellation_date"));
				} else {
					Log.d(TAG, "Not exist this key! cancellation_date");
				}
				if (childJsonObject.has("file_number")) {
					map.put("file_number",
							childJsonObject.getString("file_number"));
				} else {
					Log.d(TAG, "Not exist this key! file_number");
				}
				if (childJsonObject.has("dwelling_place")) {
					map.put("dwelling_place",
							childJsonObject.getString("dwelling_place"));
				} else {
					Log.d(TAG, "Not exist this key! dwelling_place");
				}
				if (childJsonObject.has("original_position")) {
					map.put("original_position",
							childJsonObject.getString("original_position"));
				} else {
					Log.d(TAG, "Not exist this key! original_position");
				}
				if (childJsonObject.has("issuing_authority")) {
					map.put("issuing_authority",
							childJsonObject.getString("issuing_authority"));
				} else {
					Log.d(TAG, "Not exist this key! issuing_authority");
				}
				if (childJsonObject.has("train_institutions")) {
					map.put("train_institutions",
							childJsonObject.getString("train_institutions"));
				} else {
					Log.d(TAG, "Not exist this key! train_institutions");
				}
				if (childJsonObject.has("issuer")) {
					map.put("issuer", childJsonObject.getString("issuer"));
				} else {
					Log.d(TAG, "Not exist this key! issuer");
				}
				if (childJsonObject.has("crew_job")) {
					map.put("crew_job", childJsonObject.getString("crew_job"));
				} else {
					Log.d(TAG, "Not exist this key! crew_job");
				}
				if (childJsonObject.has("report_city")) {
					map.put("report_city",
							childJsonObject.getString("report_city"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("report_county")) {
					map.put("report_county",
							childJsonObject.getString("report_county"));
				} else {
					Log.d(TAG, "Not exist this key!");
				}
				if (childJsonObject.has("issue_date")) {
					map.put("issue_date",
							childJsonObject.getString("issue_date"));
				} else {
					Log.d(TAG, "Not exist this key! issue_date");
				}
			} else {
				return null;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	public ArrayList<HashMap<String, String>> getbasicShipPortInfo(String result) {

		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

		JSONObject jsonObject;
		JSONArray jsonArray;
		try {
			jsonObject = new JSONObject(result);
			String state = null;
			if (jsonObject.has("STATE")) {
				state = jsonObject.getString("STATE");
			}

			if ("1".equalsIgnoreCase(state) && jsonObject.has("SHIP_INFO")) {

				jsonArray = jsonObject.getJSONArray("SHIP_INFO");
				for (int i = 0; i < jsonArray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject object = jsonArray.getJSONObject(i);
					if (object.has("id")) {
						map.put("id", object.getString("id"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("name")) {
						map.put("name", object.getString("name"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("city")) {
						map.put("city", object.getString("city"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("county")) {
						map.put("county", object.getString("county"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("port_Level")) {
						map.put("port_Level", object.getString("port_Level"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("institution_Name")) {
						map.put("institution_Name",
								object.getString("institution_Name"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("address")) {
						map.put("address", object.getString("address"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("land_Area")) {
						map.put("land_Area", object.getString("land_Area"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("sea_Area")) {
						map.put("sea_Area", object.getString("sea_Area"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("accommodate_Number")) {
						map.put("accommodate_Number",
								object.getString("accommodate_Number"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					list.add(map);
				}

			} else {
				return null;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public HashMap<String, String> getShipPortdetailInfo(String result) {

		HashMap<String, String> map = new HashMap<String, String>();

		JSONObject jsonObject, childJsonObject;
		JSONArray jsonArray;

		try {
			jsonObject = new JSONObject(result);
			String state = null;
			if (jsonObject.has("STATE")) {
				state = jsonObject.getString("STATE");
			}

			if ("1".equalsIgnoreCase(state) && jsonObject.has("SHIP_INFO")) {

				jsonArray = jsonObject.getJSONArray("SHIP_INFO");
				childJsonObject = jsonArray.getJSONObject(0);
				if (childJsonObject.has("id")) {
					map.put("id", childJsonObject.getString("id"));
				} else {
					Log.d(TAG, "Not exist this key id!");
				}
				if (childJsonObject.has("port_name")) {
					map.put("port_name", childJsonObject.getString("port_name"));
				} else {
					Log.d(TAG, "Not exist this key port_name!");
				}
				if (childJsonObject.has("city")) {
					map.put("city", childJsonObject.getString("city"));
				} else {
					Log.d(TAG, "Not exist this key city!");
				}
				if (childJsonObject.has("county")) {
					map.put("county", childJsonObject.getString("county"));
				} else {
					Log.d(TAG, "Not exist this key county!");
				}

				if (childJsonObject.has("regulation_time")) {
					map.put("regulation_time",
							childJsonObject.getString("regulation_time"));
				} else {
					Log.d(TAG, "Not exist this key regulation_time!");
				}
				if (childJsonObject.has("regulation_land")) {
					map.put("regulation_land",
							childJsonObject.getString("regulation_land"));
				} else {
					Log.d(TAG, "Not exist this key regulation_land!");
				}
				if (childJsonObject.has("regulation_sea")) {
					map.put("regulation_sea",
							childJsonObject.getString("regulation_sea"));
				} else {
					Log.d(TAG, "Not exist this key regulation_sea!");
				}
				if (childJsonObject.has("border_description")) {
					map.put("border_description",
							childJsonObject.getString("border_description"));
				} else {
					Log.d(TAG, "Not exist this key border_description!");
				}
				if (childJsonObject.has("border_coordinate")) {
					map.put("border_coordinate",
							childJsonObject.getString("border_coordinate"));
				} else {
					Log.d(TAG, "Not exist this key border_coordinate!");
				}
				if (childJsonObject.has("border_approve")) {
					map.put("border_approve",
							childJsonObject.getString("border_approve"));
				} else {
					Log.d(TAG, "Not exist this key border_approve!");
				}
				if (childJsonObject.has("border_time")) {
					map.put("border_time",
							childJsonObject.getString("border_time"));
				} else {
					Log.d(TAG, "Not exist this key border_time!");
				}
				if (childJsonObject.has("institution_name")) {
					map.put("institution_name",
							childJsonObject.getString("institution_name"));
				} else {
					Log.d(TAG, "Not exist this key institution_name!");
				}
				if (childJsonObject.has("institution_property")) {
					map.put("institution_property",
							childJsonObject.getString("institution_property"));
				} else {
					Log.d(TAG, "Not exist this key institution_property!");
				}
				if (childJsonObject.has("institution_person")) {
					map.put("institution_person",
							childJsonObject.getString("institution_person"));
				} else {
					Log.d(TAG, "Not exist this key institution_person!");
				}
				if (childJsonObject.has("institution_equipment")) {
					map.put("institution_equipment",
							childJsonObject.getString("institution_equipment"));
				} else {
					Log.d(TAG, "Not exist this key institution_equipment!");
				}
				if (childJsonObject.has("address")) {
					map.put("address", childJsonObject.getString("address"));
				} else {
					Log.d(TAG, "Not exist this key address!");
				}
				if (childJsonObject.has("center_coordinate")) {
					map.put("center_coordinate",
							childJsonObject.getString("center_coordinate"));
				} else {
					Log.d(TAG, "Not exist this key center_coordinate!");
				}
				if (childJsonObject.has("longitude")) {
					map.put("longitude", childJsonObject.getString("longitude"));
				} else {
					Log.d(TAG, "Not exist this key longitude!");
				}
				if (childJsonObject.has("latitude")) {
					map.put("latitude", childJsonObject.getString("latitude"));
				} else {
					Log.d(TAG, "Not exist this key latitude!");
				}
				if (childJsonObject.has("construction")) {
					map.put("construction",
							childJsonObject.getString("construction"));
				} else {
					Log.d(TAG, "Not exist this key construction!");
				}
				if (childJsonObject.has("total_investment")) {
					map.put("total_investment",
							childJsonObject.getString("total_investment"));
				} else {
					Log.d(TAG, "Not exist this key total_investment!");
				}
				if (childJsonObject.has("construction_details")) {
					map.put("construction_details",
							childJsonObject.getString("construction_details"));
				} else {
					Log.d(TAG, "Not exist this key construction_details!");
				}
				if (childJsonObject.has("land_area")) {
					map.put("land_area", childJsonObject.getString("land_area"));
				} else {
					Log.d(TAG, "Not exist this key land_area!");
				}
				if (childJsonObject.has("sea_area")) {
					map.put("sea_area", childJsonObject.getString("sea_area"));
				} else {
					Log.d(TAG, "Not exist this key! sea_area");
				}
				if (childJsonObject.has("sheltered_rating")) {
					map.put("sheltered_rating",
							childJsonObject.getString("sheltered_rating"));
				} else {
					Log.d(TAG, "Not exist this key! sheltered_rating");
				}
				if (childJsonObject.has("sheltered_sea_area")) {
					map.put("sheltered_sea_area",
							childJsonObject.getString("sheltered_sea_area"));
				} else {
					Log.d(TAG, "Not exist this key! sheltered_sea_area");
				}
				if (childJsonObject.has("accommodate_number")) {
					map.put("accommodate_number",
							childJsonObject.getString("accommodate_number"));
				} else {
					Log.d(TAG, "Not exist this key! accommodate_number");
				}
				if (childJsonObject.has("building_structure")) {
					map.put("building_structure",
							childJsonObject.getString("building_structure"));
				} else {
					Log.d(TAG, "Not exist this key! building_structure");
				}
				if (childJsonObject.has("building_area")) {
					map.put("building_area",
							childJsonObject.getString("building_area"));
				} else {
					Log.d(TAG, "Not exist this key! building_area");
				}
				if (childJsonObject.has("building_completion_time")) {
					map.put("building_completion_time", childJsonObject
							.getString("building_completion_time"));
				} else {
					Log.d(TAG, "Not exist this key! building_completion_time");
				}
				if (childJsonObject.has("building_investment")) {
					map.put("building_investment",
							childJsonObject.getString("building_investment"));
				} else {
					Log.d(TAG, "Not exist this key! building_investment");
				}
				if (childJsonObject.has("building_owner")) {
					map.put("building_owner",
							childJsonObject.getString("building_owner"));
				} else {
					Log.d(TAG, "Not exist this key! building_owner");
				}
				if (childJsonObject.has("building_manager")) {
					map.put("building_manager",
							childJsonObject.getString("building_manager"));
				} else {
					Log.d(TAG, "Not exist this key! building_manager");
				}
				if (childJsonObject.has("monitor_equipment")) {
					map.put("monitor_equipment",
							childJsonObject.getString("monitor_equipment"));
				} else {
					Log.d(TAG, "Not exist this key! monitor_equipment");
				}
				if (childJsonObject.has("monitor_model")) {
					map.put("monitor_model",
							childJsonObject.getString("monitor_model"));
				} else {
					Log.d(TAG, "Not exist this key! monitor_model");
				}
				if (childJsonObject.has("monitor_number")) {
					map.put("monitor_number",
							childJsonObject.getString("monitor_number"));
				} else {
					Log.d(TAG, "Not exist this key! monitor_number");
				}
				if (childJsonObject.has("monitor_time")) {
					map.put("monitor_time",
							childJsonObject.getString("monitor_time"));
				} else {
					Log.d(TAG, "Not exist this key! monitor_time");
				}
				if (childJsonObject.has("hydrological_model")) {
					map.put("hydrological_model",
							childJsonObject.getString("hydrological_model"));
				} else {
					Log.d(TAG, "Not exist this key! hydrological_model");
				}
				if (childJsonObject.has("hydrological_time")) {
					map.put("hydrological_time",
							childJsonObject.getString("hydrological_time"));
				} else {
					Log.d(TAG, "Not exist this key! hydrological_time");
				}
				if (childJsonObject.has("oil_collection")) {
					map.put("oil_collection",
							childJsonObject.getString("oil_collection"));
				} else {
					Log.d(TAG, "Not exist this key! oil_collection");
				}
				if (childJsonObject.has("oil_collection_model")) {
					map.put("oil_collection_model",
							childJsonObject.getString("oil_collection_model"));
				} else {
					Log.d(TAG, "Not exist this key! oil_collection_model");
				}
				if (childJsonObject.has("oil_collection_ability")) {
					map.put("oil_collection_ability",
							childJsonObject.getString("oil_collection_ability"));
				} else {
					Log.d(TAG, "Not exist this key! oil_collection_ability");
				}
				if (childJsonObject.has("uninstall_amount")) {
					map.put("uninstall_amount",
							childJsonObject.getString("uninstall_amount"));
				} else {
					Log.d(TAG, "Not exist this key! uninstall_amount");
				}
				if (childJsonObject.has("dock_number")) {
					map.put("dock_number",
							childJsonObject.getString("dock_number"));
				} else {
					Log.d(TAG, "Not exist this key! dock_number");
				}
				if (childJsonObject.has("coastline_length")) {
					map.put("coastline_length",
							childJsonObject.getString("coastline_length"));
				} else {
					Log.d(TAG, "Not exist this key! coastline_length");
				}
				if (childJsonObject.has("state")) {
					map.put("state", childJsonObject.getString("state"));
				} else {
					Log.d(TAG, "Not exist this key! uninstall_amount");
				}
				if (childJsonObject.has("border_coordinate2")) {
					map.put("border_coordinate2",
							childJsonObject.getString("border_coordinate2"));
				} else {
					Log.d(TAG, "Not exist this key! border_coordinate2");
				}
				if (childJsonObject.has("port_level")) {
					map.put("port_level",
							childJsonObject.getString("port_level"));
				} else {
					Log.d(TAG, "Not exist this key! port_level");
				}

			} else {
				return null;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	// Ͷ������Ϣ
	public ArrayList<HashMap<String, String>> getbasicPlyInfo(String result) {

		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

		JSONObject jsonObject;
		JSONArray jsonArray;
		try {
			jsonObject = new JSONObject(result);
			String state = null;
			if (jsonObject.has("STATE")) {
				state = jsonObject.getString("STATE");
			}

			if ("1".equalsIgnoreCase(state) && jsonObject.has("SHIP_INFO")) {

				jsonArray = jsonObject.getJSONArray("SHIP_INFO");
				for (int i = 0; i < jsonArray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject object = jsonArray.getJSONObject(i);
					if (object.has("id")) {
						map.put("id", object.getString("id"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("c_boat_name")) {
						map.put("c_boat_name", object.getString("c_boat_name"));
					} else {
						Log.d(TAG, "Not exist this key! c_boat_name");
					}
					if (object.has("c_boat_cde")) {
						map.put("c_boat_cde", object.getString("c_boat_cde"));
					} else {
						Log.d(TAG, "Not exist this key! c_boat_cde");
					}
					if (object.has("c_ply_app_no")) {
						map.put("c_ply_app_no",
								object.getString("c_ply_app_no"));
					} else {
						Log.d(TAG, "Not exist this key! c_ply_app_no");
					}
					if (object.has("c_nme_cn")) {
						map.put("c_nme_cn", object.getString("c_nme_cn"));
					} else {
						Log.d(TAG, "Not exist this key! c_nme_cn");
					}
					if (object.has("c_app_nme")) {
						map.put("c_app_nme", object.getString("c_app_nme"));
					} else {
						Log.d(TAG, "Not exist this key! c_app_nme");
					}
					if (object.has("t_insrnc_bgn_tm")) {
						String tmp = object.getString("t_insrnc_bgn_tm");
						tmp = tmp.substring(tmp.indexOf("(") + 1,
								tmp.indexOf("+"));
						map.put("t_insrnc_bgn_tm", getDateFromTime(tmp)
								.substring(0, 11));
					} else {
						Log.d(TAG, "Not exist this key! t_insrnc_bgn_tm");
					}
					if (object.has("T_insrnc_end_tm")) {
						String tmp = object.getString("T_insrnc_end_tm");
						tmp = tmp.substring(tmp.indexOf("(") + 1,
								tmp.indexOf("+"));
						map.put("T_insrnc_end_tm", getDateFromTime(tmp)
								.substring(0, 11));
					} else {
						Log.d(TAG, "Not exist this key! T_insrnc_end_tm");
					}
					if (object.has("c_orig_flg")) {
						String tmp = object.getString("c_orig_flg");
						if ("0".equalsIgnoreCase(tmp)) {
							tmp = "�±�";
						} else if ("1".equalsIgnoreCase(tmp)) {
							tmp = "��";
						} else {
							tmp = "";
						}
						map.put("c_orig_flg", tmp);
					} else {
						Log.d(TAG, "Not exist this key! c_orig_flg");
					}

					if (object.has("c_is_owe_money")) {
						String temp = object.getString("c_is_owe_money");
						if ("0".equals(temp)) {
							temp = "��";
						} else if ("1".equals(temp)) {
							temp = "��";
						} else {
							temp = "";
						}
						map.put("c_is_owe_money", temp);
					} else {
						Log.d(TAG, "Not exist this key c_is_owe_money!");
					}
					list.add(map);
				}
			} else {
				return null;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// Ͷ����ϸ��Ϣ
	public HashMap<String, String> getPlydetailInfo(String result) {

		HashMap<String, String> map = new HashMap<String, String>();

		JSONObject jsonObject, childJsonObject;
		JSONArray jsonArray;

		try {
			jsonObject = new JSONObject(result);
			String state = null;
			if (jsonObject.has("STATE")) {
				state = jsonObject.getString("STATE");
			}

			if ("1".equalsIgnoreCase(state) && jsonObject.has("SHIP_INFO")) {

				jsonArray = jsonObject.getJSONArray("SHIP_INFO");
				childJsonObject = jsonArray.getJSONObject(0);
				if (childJsonObject.has("id")) {
					map.put("id", childJsonObject.getString("id"));
				} else {
					Log.d(TAG, "Not exist this key id!");
				}
				if (childJsonObject.has("c_ply_app_no")) {
					map.put("c_ply_app_no",
							childJsonObject.getString("c_ply_app_no"));
				} else {
					Log.d(TAG, "Not exist this key c_ply_app_no!");
				}
				if (childJsonObject.has("c_ply_no")) {
					map.put("c_ply_no", childJsonObject.getString("c_ply_no"));
				} else {
					Log.d(TAG, "Not exist this key c_ply_no!");
				}
				if (childJsonObject.has("c_nme_cn")) {
					map.put("c_nme_cn", childJsonObject.getString("c_nme_cn"));
				} else {
					Log.d(TAG, "Not exist this key c_nme_cn!");
				}

				if (childJsonObject.has("c_boat_cde")) {
					map.put("c_boat_cde",
							childJsonObject.getString("c_boat_cde"));
				} else {
					Log.d(TAG, "Not exist this key c_boat_cde!");
				}
				if (childJsonObject.has("c_boat_name")) {
					map.put("c_boat_name",
							childJsonObject.getString("c_boat_name"));
				} else {
					Log.d(TAG, "Not exist this key c_boat_name!");
				}
				if (childJsonObject.has("c_app_cde")) {
					map.put("c_app_cde", childJsonObject.getString("c_app_cde"));
				} else {
					Log.d(TAG, "Not exist this key c_app_cde!");
				}
				if (childJsonObject.has("c_app_nme")) {
					map.put("c_app_nme", childJsonObject.getString("c_app_nme"));
				} else {
					Log.d(TAG, "Not exist this key c_app_nme!");
				}
				if (childJsonObject.has("t_insrnc_bgn_tm")) {
					map.put("t_insrnc_bgn_tm",
							childJsonObject.getString("t_insrnc_bgn_tm"));
				} else {
					Log.d(TAG, "Not exist this key t_insrnc_bgn_tm!");
				}
				if (childJsonObject.has("T_insrnc_end_tm")) {
					map.put("T_insrnc_end_tm",
							childJsonObject.getString("T_insrnc_end_tm"));
				} else {
					Log.d(TAG, "Not exist this key T_insrnc_end_tm!");
				}
				if (childJsonObject.has("c_is_owe_money")) {
					String temp = childJsonObject.getString("c_is_owe_money");
					if ("0".equals(temp)) {
						temp = "��";
					} else if ("1".equals(temp)) {
						temp = "��";
					} else {
						temp = "";
					}
					map.put("c_is_owe_money", temp);
				} else {
					Log.d(TAG, "Not exist this key c_is_owe_money!");
				}
				if (childJsonObject.has("c_udr_mrk")) {
					map.put("c_udr_mrk", childJsonObject.getString("c_udr_mrk"));
				} else {
					Log.d(TAG, "Not exist this key c_udr_mrk!");
				}
				if (childJsonObject.has("t_app_udr_tm")) {
					map.put("t_app_udr_tm",
							childJsonObject.getString("t_app_udr_tm"));
				} else {
					Log.d(TAG, "Not exist this key t_app_udr_tm!");
				}
				if (childJsonObject.has("t_udr_date")) {
					map.put("t_udr_date",
							childJsonObject.getString("t_udr_date"));
				} else {
					Log.d(TAG, "Not exist this key t_udr_date!");
				}
				if (childJsonObject.has("c_new_flg")) {
					map.put("c_new_flg", childJsonObject.getString("c_new_flg"));
				} else {
					Log.d(TAG, "Not exist this key c_new_flg!");
				}
				if (childJsonObject.has("c_orig_flg")) {
					String tmp = childJsonObject.getString("c_orig_flg");
					if ("0".equalsIgnoreCase(tmp)) {
						tmp = "�±�";
					} else if ("1".equalsIgnoreCase(tmp)) {
						tmp = "��";
					} else {
						tmp = "";
					}
					map.put("c_orig_flg", tmp);
				} else {
					Log.d(TAG, "Not exist this key c_orig_flg!");
				}
				if (childJsonObject.has("c_edr_type")) {
					map.put("c_edr_type",
							childJsonObject.getString("c_edr_type"));
				} else {
					Log.d(TAG, "Not exist this key c_edr_type!");
				}
				if (childJsonObject.has("n_prm")) {
					map.put("n_prm", childJsonObject.getString("n_prm"));
				} else {
					Log.d(TAG, "Not exist this key n_prm!");
				}
				if (childJsonObject.has("n_amt")) {
					map.put("n_amt", childJsonObject.getString("n_amt"));
				} else {
					Log.d(TAG, "Not exist this key n_amt!");
				}
				if (childJsonObject.has("n_rate")) {
					map.put("n_rate", childJsonObject.getString("n_rate"));
				} else {
					Log.d(TAG, "Not exist this key n_rate!");
				}
				if (childJsonObject.has("CREATE_USER")) {
					map.put("CREATE_USER",
							childJsonObject.getString("CREATE_USER"));
				} else {
					Log.d(TAG, "Not exist this key CREATE_USER!");
				}
				if (childJsonObject.has("CREATE_DATE")) {
					map.put("CREATE_DATE",
							childJsonObject.getString("CREATE_DATE"));
				} else {
					Log.d(TAG, "Not exist this key CREATE_DATE!");
				}
				if (childJsonObject.has("UPDATE_USER")) {
					map.put("UPDATE_USER",
							childJsonObject.getString("UPDATE_USER"));
				} else {
					Log.d(TAG, "Not exist this key UPDATE_USER!");
				}
				if (childJsonObject.has("UPDATE_DATE")) {
					map.put("UPDATE_DATE",
							childJsonObject.getString("UPDATE_DATE"));
				} else {
					Log.d(TAG, "Not exist this key! UPDATE_DATE");
				}
			} else {
				return null;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	// �������Ϣ
	public ArrayList<HashMap<String, String>> getbasicClmInfo(String result) {

		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

		JSONObject jsonObject;
		JSONArray jsonArray;
		try {
			jsonObject = new JSONObject(result);
			String state = null;
			if (jsonObject.has("STATE")) {
				state = jsonObject.getString("STATE");
			}

			if ("1".equalsIgnoreCase(state) && jsonObject.has("SHIP_INFO")) {

				jsonArray = jsonObject.getJSONArray("SHIP_INFO");
				for (int i = 0; i < jsonArray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject object = jsonArray.getJSONObject(i);
					if (object.has("id")) {
						map.put("id", object.getString("id"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("c_boat_name")) {
						map.put("c_boat_name", object.getString("c_boat_name"));
					} else {
						Log.d(TAG, "Not exist this key! c_boat_name");
					}
					if (object.has("c_boat_cde")) {
						map.put("c_boat_cde", object.getString("c_boat_cde"));
					} else {
						Log.d(TAG, "Not exist this key! c_boat_cde");
					}
					if (object.has("c_clm_no")) {
						map.put("c_clm_no", object.getString("c_clm_no"));
					} else {
						Log.d(TAG, "Not exist this key! c_clm_no");
					}
					if (object.has("n_pay_tms")) {
						map.put("n_pay_tms", object.getString("n_pay_tms"));
					} else {
						Log.d(TAG, "Not exist this key! n_pay_tms");
					}
					if (object.has("c_app_nme")) {
						map.put("c_app_nme", object.getString("c_app_nme"));
					} else {
						Log.d(TAG, "Not exist this key! c_app_nme");
					}
					if (object.has("C_prod_name")) {
						map.put("C_prod_name", object.getString("C_prod_name"));
					} else {
						Log.d(TAG, "Not exist this key! C_prod_name");
					}
					if (object.has("n_sum_clm_amt")) {
						map.put("n_sum_clm_amt",
								object.getString("n_sum_clm_amt"));
					} else {
						Log.d(TAG, "Not exist this key! n_sum_clm_amt");
					}
					if (object.has("c_clm_mainstatus")) {
						map.put("c_clm_mainstatus",
								object.getString("c_clm_mainstatus"));
					} else {
						Log.d(TAG, "Not exist this key! c_clm_mainstatus");
					}
					if (object.has("t_clm_rgst_tm")) {
						String temp = object.getString("t_clm_rgst_tm");
						if (null != temp && temp.contains("(")
								&& temp.contains("+")) {
							temp = temp.substring(temp.indexOf("(") + 1,
									temp.indexOf("+"));
							temp = getDateFromTime(temp).substring(0, 11);
						} else {
							temp = "";
						}
						map.put("t_clm_rgst_tm", temp);
					} else {
						Log.d(TAG, "Not exist this key! t_clm_rgst_tm");
					}
					if (object.has("t_cls_tm")) {
						String temp = object.getString("t_cls_tm");
						if (null != temp && temp.contains("(")
								&& temp.contains("+")) {
							temp = temp.substring(temp.indexOf("(") + 1,
									temp.indexOf("+"));
							temp = getDateFromTime(temp).substring(0, 11);
						} else {
							temp = "";
						}
						map.put("t_cls_tm", temp);
					} else {
						Log.d(TAG, "Not exist this key! t_cls_tm");
					}
					list.add(map);
				}
			} else {
				return null;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// ������ϸ��Ϣ
	public HashMap<String, String> getClmdetailInfo(String result) {

		HashMap<String, String> map = new HashMap<String, String>();

		JSONObject jsonObject, childJsonObject;
		JSONArray jsonArray;

		try {
			jsonObject = new JSONObject(result);
			String state = null;
			if (jsonObject.has("STATE")) {
				state = jsonObject.getString("STATE");
			}

			if ("1".equalsIgnoreCase(state) && jsonObject.has("SHIP_INFO")) {

				jsonArray = jsonObject.getJSONArray("SHIP_INFO");
				childJsonObject = jsonArray.getJSONObject(0);
				if (childJsonObject.has("id")) {
					map.put("id", childJsonObject.getString("id"));
				} else {
					Log.d(TAG, "Not exist this key id!");
				}
				if (childJsonObject.has("c_clm_no")) {
					map.put("c_clm_no", childJsonObject.getString("c_clm_no"));
				} else {
					Log.d(TAG, "Not exist this key c_clm_no!");
				}
				if (childJsonObject.has("n_pay_tms")) {
					map.put("c_ply_no", childJsonObject.getString("n_pay_tms"));
				} else {
					Log.d(TAG, "Not exist this key c_ply_no!");
				}
				if (childJsonObject.has("c_ply_no")) {
					map.put("c_ply_no", childJsonObject.getString("c_ply_no"));
				} else {
					Log.d(TAG, "Not exist this key c_ply_no!");
				}
				if (childJsonObject.has("c_dpt_cde")) {
					map.put("c_dpt_cde", childJsonObject.getString("c_dpt_cde"));
				} else {
					Log.d(TAG, "Not exist this key c_dpt_cde!");
				}

				if (childJsonObject.has("c_dpt_cnm")) {
					map.put("c_dpt_cnm", childJsonObject.getString("c_dpt_cnm"));
				} else {
					Log.d(TAG, "Not exist this key c_dpt_cnm!");
				}
				if (childJsonObject.has("c_app_nme")) {
					map.put("c_app_nme", childJsonObject.getString("c_app_nme"));
				} else {
					Log.d(TAG, "Not exist this key c_app_nme!");
				}
				if (childJsonObject.has("c_boat_name")) {
					map.put("c_boat_name",
							childJsonObject.getString("c_boat_name"));
				} else {
					Log.d(TAG, "Not exist this key c_boat_name!");
				}
				if (childJsonObject.has("c_boat_cde")) {
					map.put("c_boat_cde",
							childJsonObject.getString("c_boat_cde"));
				} else {
					Log.d(TAG, "Not exist this key c_boat_cde!");
				}
				if (childJsonObject.has("n_sum_clm_amt")) {
					map.put("n_sum_clm_amt",
							childJsonObject.getString("n_sum_clm_amt"));
				} else {
					Log.d(TAG, "Not exist this key n_sum_clm_amt!");
				}
				if (childJsonObject.has("c_prod_name")) {
					map.put("c_prod_name",
							childJsonObject.getString("c_prod_name"));
				} else {
					Log.d(TAG, "Not exist this key c_prod_name!");
				}
				if (childJsonObject.has("c_clm_mainstatus")) {
					map.put("c_clm_mainstatus",
							childJsonObject.getString("c_clm_mainstatus"));
				} else {
					Log.d(TAG, "Not exist this key c_clm_mainstatus!");
				}
				if (childJsonObject.has("t_clm_rgst_tm")) {
					map.put("t_clm_rgst_tm",
							childJsonObject.getString("t_clm_rgst_tm"));
				} else {
					Log.d(TAG, "Not exist this key t_clm_rgst_tm!");
				}
				if (childJsonObject.has("t_cls_tm")) {
					map.put("t_cls_tm", childJsonObject.getString("t_cls_tm"));
				} else {
					Log.d(TAG, "Not exist this key t_cls_tm!");
				}
				if (childJsonObject.has("c_eac_dcm_no")) {
					map.put("c_eac_dcm_no",
							childJsonObject.getString("c_eac_dcm_no"));
				} else {
					Log.d(TAG, "Not exist this key c_eac_dcm_no!");
				}
				if (childJsonObject.has("CREATE_USER")) {
					map.put("CREATE_USER",
							childJsonObject.getString("CREATE_USER"));
				} else {
					Log.d(TAG, "Not exist this key CREATE_USER!");
				}
				if (childJsonObject.has("CREATE_DATE")) {
					map.put("CREATE_DATE",
							childJsonObject.getString("CREATE_DATE"));
				} else {
					Log.d(TAG, "Not exist this key CREATE_DATE!");
				}
				if (childJsonObject.has("UPDATE_USER")) {
					map.put("UPDATE_USER",
							childJsonObject.getString("UPDATE_USER"));
				} else {
					Log.d(TAG, "Not exist this key UPDATE_USER!");
				}
				if (childJsonObject.has("UPDATE_DATE")) {
					map.put("UPDATE_DATE",
							childJsonObject.getString("UPDATE_DATE"));
				} else {
					Log.d(TAG, "Not exist this key UPDATE_DATE!");
				}
			} else {
				return null;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	// �洬���׻���Ϣ
	public ArrayList<HashMap<String, String>> getbasicTradingInfo(String result) {

		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

		JSONObject jsonObject;
		JSONArray jsonArray;
		try {
			jsonObject = new JSONObject(result);
			String state = null;
			if (jsonObject.has("STATE")) {
				state = jsonObject.getString("STATE");
			}

			if ("1".equalsIgnoreCase(state) && jsonObject.has("SHIP_INFO")) {

				jsonArray = jsonObject.getJSONArray("SHIP_INFO");
				for (int i = 0; i < jsonArray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject object = jsonArray.getJSONObject(i);
					if (object.has("id")) {
						map.put("id", object.getString("id"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("c_boat_name")) {
						map.put("c_boat_name", object.getString("c_boat_name"));
					} else {
						Log.d(TAG, "Not exist this key! c_boat_name");
					}
					if (object.has("c_boat_num")) {
						map.put("c_boat_num", object.getString("c_boat_num"));
					} else {
						Log.d(TAG, "Not exist this key! c_boat_num");
					}
					if (object.has("c_prn_no")) {
						map.put("c_prn_no", object.getString("c_prn_no"));
					} else {
						Log.d(TAG, "Not exist this key! c_prn_no");
					}
					if (object.has("c_delfalg")) {

						String temp = object.getString("c_delfalg");
						if ("0".equals(temp)) {
							temp = "��";
						} else if ("1".equals(temp)) {
							temp = "ɾ��";
						} else if ("2".equals(temp)) {
							temp = "����";
						} else {
							temp = "";
						}
						map.put("c_delfalg", temp);

					} else {
						Log.d(TAG, "Not exist this key! c_delfalg");
					}
					if (object.has("n_transfer_price_lower")) {
						map.put("n_transfer_price_lower",
								object.getString("n_transfer_price_lower"));
					} else {
						Log.d(TAG, "Not exist this key! n_transfer_price_lower");
					}
					if (object.has("c_signed_addr")) {
						map.put("c_signed_addr",
								object.getString("c_signed_addr"));
					} else {
						Log.d(TAG, "Not exist this key! c_signed_addr");
					}
					if (object.has("C_22_deal_mode")) {

						String temp = object.getString("C_22_deal_mode");
						if ("0".equals(temp)) {
							temp = "ֱ��Ͷ����ҵ���";
						} else if ("1".equals(temp)) {
							temp = "���¸���";
						} else if ("2".equals(temp)) {
							temp = "���";
						} else if ("3".equals(temp)) {
							temp = "����";
						} else {
							temp = "";
						}
						map.put("C_22_deal_mode", temp);
					} else {
						Log.d(TAG, "Not exist this key! C_22_deal_mode");
					}
					if (object.has("c_accept_dpt_name")) {

						String temp = object.getString("c_delfalg");
						if ("0".equals(temp)) {
							temp = "��";
						} else if ("1".equals(temp)) {
							temp = "ɾ��";
						} else if ("2".equals(temp)) {
							temp = "����";
						} else {
							temp = "";
						}
						map.put("c_accept_dpt_name", temp);
					} else {
						Log.d(TAG, "Not exist this key! c_accept_dpt_name");
					}
					if (object.has("t_signed_date")) {
						String temp = object.getString("t_signed_date");
						if (null != temp && temp.contains("(")
								&& temp.contains("+")) {
							temp = temp.substring(temp.indexOf("(") + 1,
									temp.indexOf("+"));
							temp = getDateFromTime(temp).substring(0, 11);
						} else {
							temp = "";
						}
						map.put("t_signed_date", temp);
					} else {
						Log.d(TAG, "Not exist this key! t_signed_date");
					}
					if (object.has("c_seller_name")) {
						map.put("c_seller_name",
								object.getString("c_seller_name"));
					} else {
						Log.d(TAG, "Not exist this key! c_seller_name");
					}
					if (object.has("c_buyer_name")) {
						map.put("c_buyer_name",
								object.getString("c_buyer_name"));
					} else {
						Log.d(TAG, "Not exist this key! c_buyer_name");
					}

					list.add(map);
				}
			} else {
				return null;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// �洬������ϸ��Ϣ
	public HashMap<String, String> getTradingdetailInfo(String result) {

		HashMap<String, String> map = new HashMap<String, String>();

		JSONObject jsonObject, childJsonObject;
		JSONArray jsonArray;

		try {
			jsonObject = new JSONObject(result);
			String state = null;
			if (jsonObject.has("STATE")) {
				state = jsonObject.getString("STATE");
			}

			if ("1".equalsIgnoreCase(state) && jsonObject.has("SHIP_INFO")) {

				jsonArray = jsonObject.getJSONArray("SHIP_INFO");
				childJsonObject = jsonArray.getJSONObject(0);
				if (childJsonObject.has("id")) {
					map.put("id", childJsonObject.getString("id"));
				} else {
					Log.d(TAG, "Not exist this key id!");
				}
				if (childJsonObject.has("c_con_no")) {
					map.put("c_con_no", childJsonObject.getString("c_con_no"));
				} else {
					Log.d(TAG, "Not exist this key c_con_no!");
				}

				if (childJsonObject.has("c_prn_no")) {
					map.put("c_prn_no", childJsonObject.getString("c_prn_no"));
				} else {
					Log.d(TAG, "Not exist this key c_prn_no!");
				}

				if (childJsonObject.has("c_delfalg")) {
					map.put("c_delfalg", childJsonObject.getString("c_delfalg"));
				} else {
					Log.d(TAG, "Not exist this key c_delfalg!");
				}
				if (childJsonObject.has("matter_name")) {
					map.put("matter_name",
							childJsonObject.getString("matter_name"));
				} else {
					Log.d(TAG, "Not exist this key matter_name!");
				}

				if (childJsonObject.has("c_boat_name")) {
					map.put("c_boat_name",
							childJsonObject.getString("c_boat_name"));
				} else {
					Log.d(TAG, "Not exist this key c_boat_name!");
				}

				if (childJsonObject.has("c_boat_num")) {
					map.put("c_boat_num",
							childJsonObject.getString("c_boat_num"));
				} else {
					Log.d(TAG, "Not exist this key c_boat_num!");
				}

				if (childJsonObject.has("n_22_jia_prop")) {
					map.put("n_22_jia_prop",
							childJsonObject.getString("n_22_jia_prop"));
				} else {
					Log.d(TAG, "Not exist this key n_22_jia_prop!");
				}

				if (childJsonObject.has("n_transfer_price_lower")) {
					map.put("n_transfer_price_lower",
							childJsonObject.getString("n_transfer_price_lower"));
				} else {
					Log.d(TAG, "Not exist this key n_transfer_price_lower!");
				}

				if (childJsonObject.has("c_22_deal_mode")) {
					String temp = childJsonObject.getString("c_22_deal_mode");
					if ("0".equals(temp)) {
						temp = "ֱ��Ͷ����ҵ���";
					} else if ("1".equals(temp)) {
						temp = "���¸���";
					} else if ("2".equals(temp)) {
						temp = "���";
					} else if ("3".equals(temp)) {
						temp = "����";
					} else {
						temp = "";
					}
					map.put("c_22_deal_mode", temp);
				} else {
					Log.d(TAG, "Not exist this key c_22_deal_mode!");
				}

				if (childJsonObject.has("c_signed_addr")) {
					map.put("c_signed_addr",
							childJsonObject.getString("c_signed_addr"));
				} else {
					Log.d(TAG, "Not exist this key c_signed_addr!");
				}

				if (childJsonObject.has("t_signed_date")) {
					String temp = childJsonObject.getString("t_signed_date");
					if (null != temp && temp.contains("(")
							&& temp.contains("+")) {
						temp = temp.substring(temp.indexOf("(") + 1,
								temp.indexOf("+"));
						temp = getDateFromTime(temp);
					} else {
						temp = "";
					}
					map.put("t_signed_date", temp);

				} else {
					Log.d(TAG, "Not exist this key t_signed_date!");
				}

				if (childJsonObject.has("c_accept_dpt_name")) {

					map.put("c_accept_dpt_name",
							childJsonObject.getString("c_accept_dpt_name"));
				} else {
					Log.d(TAG, "Not exist this key c_accept_dpt_name!");
				}

				if (childJsonObject.has("c_seller_name")) {
					map.put("c_seller_name",
							childJsonObject.getString("c_seller_name"));
				} else {
					Log.d(TAG, "Not exist this key c_seller_name!");
				}

				if (childJsonObject.has("c_seller_card_holder")) {
					map.put("c_seller_card_holder",
							childJsonObject.getString("c_seller_card_holder"));
				} else {
					Log.d(TAG, "Not exist this key c_seller_card_holder!");
				}

				if (childJsonObject.has("c_seller_card_holder_id")) {
					map.put("c_seller_card_holder_id", childJsonObject
							.getString("c_seller_card_holder_id"));
				} else {
					Log.d(TAG, "Not exist this key c_seller_card_holder_id!");
				}

				if (childJsonObject.has("c_seller_household_register")) {
					map.put("c_seller_household_register", childJsonObject
							.getString("c_seller_household_register"));
				} else {
					Log.d(TAG,
							"Not exist this key c_seller_household_register!");
				}

				if (childJsonObject.has("c_buyer_name")) {
					map.put("c_buyer_name",
							childJsonObject.getString("c_buyer_name"));
				} else {
					Log.d(TAG, "Not exist this key c_buyer_name!");
				}

				if (childJsonObject.has("c_buyer_card_holder")) {
					map.put("c_buyer_card_holder",
							childJsonObject.getString("c_buyer_card_holder"));
				} else {
					Log.d(TAG, "Not exist this key c_buyer_card_holder!");
				}

				if (childJsonObject.has("c_buyer_card_holder_id")) {
					map.put("c_buyer_card_holder_id",
							childJsonObject.getString("c_buyer_card_holder_id"));
				} else {
					Log.d(TAG, "Not exist this key c_buyer_card_holder_id!");
				}

				if (childJsonObject.has("c_buyer_household_register")) {
					map.put("c_buyer_household_register", childJsonObject
							.getString("c_buyer_household_register"));
				} else {
					Log.d(TAG, "Not exist this key c_buyer_household_register!");
				}

				if (childJsonObject.has("buyer_link_name")) {
					map.put("buyer_link_name",
							childJsonObject.getString("buyer_link_name"));
				} else {
					Log.d(TAG, "Not exist this key buyer_link_name!");
				}

				if (childJsonObject.has("buyer_link_addr")) {
					map.put("buyer_link_addr",
							childJsonObject.getString("buyer_link_addr"));
				} else {
					Log.d(TAG, "Not exist this key buyer_link_addr!");
				}

				if (childJsonObject.has("buyer_link_tel")) {
					map.put("buyer_link_tel",
							childJsonObject.getString("buyer_link_tel"));
				} else {
					Log.d(TAG, "Not exist this key buyer_link_tel!");
				}

				if (childJsonObject.has("seller_link_name")) {
					map.put("seller_link_name",
							childJsonObject.getString("seller_link_name"));
				} else {
					Log.d(TAG, "Not exist this key seller_link_name!");
				}

				if (childJsonObject.has("seller_link_addr")) {
					map.put("seller_link_addr",
							childJsonObject.getString("seller_link_addr"));
				} else {
					Log.d(TAG, "Not exist this key seller_link_addr!");
				}

				if (childJsonObject.has("seller_link_tel")) {
					map.put("seller_link_tel",
							childJsonObject.getString("seller_link_tel"));
				} else {
					Log.d(TAG, "Not exist this key seller_link_tel!");
				}

				if (childJsonObject.has("CREATE_USER")) {
					map.put("CREATE_USER",
							childJsonObject.getString("CREATE_USER"));
				} else {
					Log.d(TAG, "Not exist this key CREATE_USER!");
				}

				if (childJsonObject.has("CREATE_DATE")) {
					map.put("CREATE_DATE",
							childJsonObject.getString("CREATE_DATE"));
				} else {
					Log.d(TAG, "Not exist this key CREATE_DATE!");
				}

				if (childJsonObject.has("UPDATE_USER")) {
					map.put("UPDATE_USER",
							childJsonObject.getString("UPDATE_USER"));
				} else {
					Log.d(TAG, "Not exist this key UPDATE_USER!");
				}

				if (childJsonObject.has("UPDATE_DATE")) {
					map.put("UPDATE_DATE",
							childJsonObject.getString("UPDATE_DATE"));
				} else {
					Log.d(TAG, "Not exist this key UPDATE_DATE!");
				}

			} else {
				return null;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	public ArrayList<HashMap<String, String>> getShipNumberByRegion(
			String result) {

		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		String state = null;
		try {
			JSONObject jsonObject = new JSONObject(result);
			if (jsonObject.has("STATE")) {
				state = jsonObject.getString("STATE");
			} else {
				Log.d(TAG, "The input data has not contain \"STATE\" key");
			}

			JSONArray shipRegionJsonArray = null, BDJsonArray = null;
			if ("1".equalsIgnoreCase(state)) {
				if (jsonObject.has("SHIP_INFO")) {
					shipRegionJsonArray = jsonObject.getJSONArray("SHIP_INFO");
				} else {
					Log.d(TAG,
							"The input data has not contain \"SHIP_INFO\" key");
				}
				for (int i = 0; i < shipRegionJsonArray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject childJsonObject = shipRegionJsonArray
							.getJSONObject(i);
					if (childJsonObject.has("Region")) {
						map.put("region", childJsonObject.getString("Region"));
					} else {
						Log.d(TAG,
								"The input data has not contain \"Region\" key");
					}
					if (childJsonObject.has("Num")) {
						map.put("number", childJsonObject.getString("Num"));
					} else {
						Log.d(TAG, "The input data has not contain \"Num\" key");
					}

					list.add(map);
				}

			} else {
				return null;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// �õ�ship_district
	public ArrayList<HashMap<String, String>> getShipDistrict(String result) {

		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		String state = null;
		try {
			JSONObject jsonObject = new JSONObject(result);
			if (jsonObject.has("STATE")) {
				state = jsonObject.getString("STATE");
			} else {
				Log.d(TAG, "The input data has not contain \"STATE\" key");
			}

			JSONArray shipRegionJsonArray = null, BDJsonArray = null;
			if ("1".equalsIgnoreCase(state)) {
				if (jsonObject.has("SHIP_INFO")) {
					shipRegionJsonArray = jsonObject.getJSONArray("SHIP_INFO");
				} else {
					Log.d(TAG,
							"The input data has not contain \"SHIP_INFO\" key");
				}
				for (int i = 0; i < shipRegionJsonArray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject childJsonObject = shipRegionJsonArray
							.getJSONObject(i);
					if (childJsonObject.has("SHIP_DISTRICT")) {
						map.put("SHIP_DISTRICT",
								childJsonObject.getString("SHIP_DISTRICT"));
					} else {
						Log.d(TAG,
								"The input data has not contain \"SHIP_DISTRICT\" key");
					}
					list.add(map);
				}

			} else {
				return null;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList<HashMap<String, String>> getShipNumberByJobType(
			String result) {

		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		String state = null;
		try {
			JSONObject jsonObject = new JSONObject(result);
			if (jsonObject.has("STATE")) {
				state = jsonObject.getString("STATE");
			} else {
				Log.d(TAG, "The input data has not contain \"STATE\" key");
			}

			JSONArray shipRegionJsonArray = null, BDJsonArray = null;
			if ("1".equalsIgnoreCase(state)) {
				if (jsonObject.has("SHIP_INFO")) {
					shipRegionJsonArray = jsonObject.getJSONArray("SHIP_INFO");
				} else {
					Log.d(TAG,
							"The input data has not contain \"SHIP_INFO\" key");
				}
				for (int i = 0; i < shipRegionJsonArray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject childJsonObject = shipRegionJsonArray
							.getJSONObject(i);
					if (childJsonObject.has("JobType")) {
						map.put("jobType", childJsonObject.getString("JobType"));
					} else {
						Log.d(TAG,
								"The input data has not contain \"JobType\" key");
					}
					if (childJsonObject.has("Num")) {
						map.put("number", childJsonObject.getString("Num"));
					} else {
						Log.d(TAG, "The input data has not contain \"Num\" key");
					}

					list.add(map);
				}

			} else {
				return null;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList<HashMap<String, String>> getShipNumberByPower(String result) {

		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		String state = null;
		try {
			JSONObject jsonObject = new JSONObject(result);
			if (jsonObject.has("STATE")) {
				state = jsonObject.getString("STATE");
			} else {
				Log.d(TAG, "The input data has not contain \"STATE\" key");
			}

			JSONArray shipRegionJsonArray = null, BDJsonArray = null;
			if ("1".equalsIgnoreCase(state)) {
				if (jsonObject.has("SHIP_INFO")) {
					shipRegionJsonArray = jsonObject.getJSONArray("SHIP_INFO");
				} else {
					Log.d(TAG,
							"The input data has not contain \"SHIP_INFO\" key");
				}
				for (int i = 0; i < shipRegionJsonArray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject childJsonObject = shipRegionJsonArray
							.getJSONObject(i);
					if (childJsonObject.has("Power")) {
						map.put("power", childJsonObject.getString("Power"));
					} else {
						Log.d(TAG,
								"The input data has not contain \"Power\" key");
					}
					if (childJsonObject.has("Data1")) {
						map.put("data", childJsonObject.getString("Data1")
								.trim());
					} else {
						Log.d(TAG,
								"The input data has not contain \"Data\" key");
					}

					list.add(map);
				}

			} else {
				return null;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList<HashMap<String, String>> getCrewNumberByRegion(
			String result) {

		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		String state = null;
		try {
			JSONObject jsonObject = new JSONObject(result);
			if (jsonObject.has("STATE")) {
				state = jsonObject.getString("STATE");
			} else {
				Log.d(TAG, "The input data has not contain \"STATE\" key");
			}

			JSONArray shipRegionJsonArray = null, BDJsonArray = null;
			if ("1".equalsIgnoreCase(state)) {
				if (jsonObject.has("SHIP_INFO")) {
					shipRegionJsonArray = jsonObject.getJSONArray("SHIP_INFO");
				} else {
					Log.d(TAG,
							"The input data has not contain \"SHIP_INFO\" key");
				}
				for (int i = 0; i < shipRegionJsonArray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject childJsonObject = shipRegionJsonArray
							.getJSONObject(i);
					if (childJsonObject.has("Region")) {
						map.put("region", childJsonObject.getString("Region"));
					} else {
						Log.d(TAG,
								"The input data has not contain \"Region\" key");
					}
					if (childJsonObject.has("Num")) {
						map.put("number", childJsonObject.getString("Num")
								.trim());
					} else {
						Log.d(TAG, "The input data has not contain \"Num\" key");
					}

					list.add(map);
				}

			} else {
				return null;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList<HashMap<String, String>> getCrewNumberByJob(String result) {

		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		String state = null;
		try {
			JSONObject jsonObject = new JSONObject(result);
			if (jsonObject.has("STATE")) {
				state = jsonObject.getString("STATE");
			} else {
				Log.d(TAG, "The input data has not contain \"STATE\" key");
			}

			JSONArray shipRegionJsonArray = null, BDJsonArray = null;
			if ("1".equalsIgnoreCase(state)) {
				if (jsonObject.has("SHIP_INFO")) {
					shipRegionJsonArray = jsonObject.getJSONArray("SHIP_INFO");
				} else {
					Log.d(TAG,
							"The input data has not contain \"SHIP_INFO\" key");
				}
				for (int i = 0; i < shipRegionJsonArray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject childJsonObject = shipRegionJsonArray
							.getJSONObject(i);
					if (childJsonObject.has("Job")) {
						map.put("job", childJsonObject.getString("Job"));
					} else {
						Log.d(TAG, "The input data has not contain \"Job\" key");
					}
					if (childJsonObject.has("Num")) {
						map.put("number", childJsonObject.getString("Num")
								.trim());
					} else {
						Log.d(TAG, "The input data has not contain \"Num\" key");
					}

					list.add(map);
				}

			} else {
				return null;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList<HashMap<String, String>> getCrewNumberByCertificate(
			String result) {

		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		String state = null;
		try {
			JSONObject jsonObject = new JSONObject(result);
			if (jsonObject.has("STATE")) {
				state = jsonObject.getString("STATE");
			} else {
				Log.d(TAG, "The input data has not contain \"STATE\" key");
			}

			JSONArray shipRegionJsonArray = null, BDJsonArray = null;
			if ("1".equalsIgnoreCase(state)) {
				if (jsonObject.has("SHIP_INFO")) {
					shipRegionJsonArray = jsonObject.getJSONArray("SHIP_INFO");
				} else {
					Log.d(TAG,
							"The input data has not contain \"SHIP_INFO\" key");
				}
				for (int i = 0; i < shipRegionJsonArray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject childJsonObject = shipRegionJsonArray
							.getJSONObject(i);
					if (childJsonObject.has("Certificate")) {
						map.put("certificate",
								childJsonObject.getString("Certificate"));
					} else {
						Log.d(TAG,
								"The input data has not contain \"Certificate\" key");
					}
					if (childJsonObject.has("Num")) {
						map.put("number", childJsonObject.getString("Num")
								.trim());
					} else {
						Log.d(TAG, "The input data has not contain \"Num\" key");
					}

					list.add(map);
				}

			} else {
				return null;
			}
			// TODO Auto-generated catch block
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList<HashMap<String, String>> getPortNumberByDisctribution(
			String result) {

		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		String state = null;
		try {
			JSONObject jsonObject = new JSONObject(result);
			if (jsonObject.has("STATE")) {
				state = jsonObject.getString("STATE");
			} else {
				Log.d(TAG, "The input data has not contain \"STATE\" key");
			}

			JSONArray shipRegionJsonArray = null, BDJsonArray = null;
			if ("1".equalsIgnoreCase(state)) {
				if (jsonObject.has("SHIP_INFO")) {
					shipRegionJsonArray = jsonObject.getJSONArray("SHIP_INFO");
				} else {
					Log.d(TAG,
							"The input data has not contain \"SHIP_INFO\" key");
				}
				for (int i = 0; i < shipRegionJsonArray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject childJsonObject = shipRegionJsonArray
							.getJSONObject(i);
					if (childJsonObject.has("City")) {
						map.put("city", childJsonObject.getString("City"));
					} else {
						Log.d(TAG,
								"The input data has not contain \"City\" key");
					}
					if (childJsonObject.has("Num")) {
						map.put("number", childJsonObject.getString("Num")
								.trim());
					} else {
						Log.d(TAG, "The input data has not contain \"Num\" key");
					}

					list.add(map);
				}

			} else {
				return null;
			}
			// TODO Auto-generated catch block
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList<HashMap<String, String>> getPortNumberByLevel(String result) {

		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		String state = null;
		try {
			JSONObject jsonObject = new JSONObject(result);
			if (jsonObject.has("STATE")) {
				state = jsonObject.getString("STATE");
			} else {
				Log.d(TAG, "The input data has not contain \"STATE\" key");
			}

			JSONArray shipRegionJsonArray = null, BDJsonArray = null;
			if ("1".equalsIgnoreCase(state)) {
				if (jsonObject.has("SHIP_INFO")) {
					shipRegionJsonArray = jsonObject.getJSONArray("SHIP_INFO");
				} else {
					Log.d(TAG,
							"The input data has not contain \"SHIP_INFO\" key");
				}
				for (int i = 0; i < shipRegionJsonArray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject childJsonObject = shipRegionJsonArray
							.getJSONObject(i);
					if (childJsonObject.has("Level")) {
						map.put("level", childJsonObject.getString("Level"));
					} else {
						Log.d(TAG,
								"The input data has not contain \"Level\" key");
					}
					if (childJsonObject.has("Num")) {
						map.put("number", childJsonObject.getString("Num")
								.trim());
					} else {
						Log.d(TAG, "The input data has not contain \"Num\" key");
					}

					list.add(map);
				}

			} else {
				return null;
			}
			// TODO Auto-generated catch block
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return list;
	}

	// ����洬�����˻�ȡ�洬��γ����Ϣ
	public ArrayList<HashMap<String, String>> getGPSByOwnerName(String result) {

		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

		JSONObject jsonObject;
		JSONArray jsonArray;
		try {
			jsonObject = new JSONObject(result);
			String state = null;
			if (jsonObject.has("STATE")) {
				state = jsonObject.getString("STATE");
			}

			if ("1".equalsIgnoreCase(state) && jsonObject.has("SHIP_INFO")) {

				jsonArray = jsonObject.getJSONArray("SHIP_INFO");
				for (int i = 0; i < jsonArray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject object = jsonArray.getJSONObject(i);
					if (object.has("ID")) {
						map.put("ID", object.getString("ID"));
					} else {
						Log.d(TAG, "Not exist this key! ID");
					}
					if (object.has("MOBILENAME")) {
						map.put("MOBILENAME", object.getString("MOBILENAME"));
					} else {
						Log.d(TAG, "Not exist this key! MOBILENAME");
					}
					if (object.has("GPS_TIME")) {
						String temp = object.getString("GPS_TIME");
						if (null != temp && temp.length() > 0) {
							temp = getDateFromTime(temp);
						} else {
							temp = "";
						}
						map.put("GPS_TIME", temp);
					} else {
						Log.d(TAG, "Not exist this key! GPS_TIME");
					}
					if (object.has("LATITUDE")) {
						map.put("LATITUDE", object.getString("LATITUDE"));
					} else {
						Log.d(TAG, "Not exist this key! LATITUDE");
					}
					if (object.has("LONGITUDE")) {
						map.put("LONGITUDE", object.getString("LONGITUDE"));
					} else {
						Log.d(TAG, "Not exist this key! LONGITUDE");
					}
					if (object.has("SPEED")) {
						map.put("SPEED", object.getString("SPEED"));
					} else {
						Log.d(TAG, "Not exist this key! SPEED");
					}
					if (object.has("UPDATEDATE")) {
						String temp = object.getString("UPDATEDATE");
						if (null != temp && temp.contains("(")
								&& temp.contains("+")) {
							temp = temp.substring(temp.indexOf("(") + 1,
									temp.indexOf("+"));
							temp = getDateFromTime(temp);
						} else {
							temp = "";
						}
						map.put("UPDATEDATE", temp);
					} else {
						Log.d(TAG, "Not exist this key! t_signed_date");
					}
					if (object.has("ORIGIN")) {
						map.put("ORIGIN", object.getString("ORIGIN"));
					} else {
						Log.d(TAG, "Not exist this key! ORIGIN");
					}
					list.add(map);
				}
			} else {
				return null;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// ����洬�����˱����ȡ�洬��γ����Ϣ
	public ArrayList<HashMap<String, String>> getGPSByOwnerNo(String result) {

		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

		JSONObject jsonObject;
		JSONArray jsonArray;
		try {
			jsonObject = new JSONObject(result);
			String state = null;
			if (jsonObject.has("STATE")) {
				state = jsonObject.getString("STATE");
			}

			if ("1".equalsIgnoreCase(state) && jsonObject.has("SHIP_INFO")) {

				jsonArray = jsonObject.getJSONArray("SHIP_INFO");
				for (int i = 0; i < jsonArray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject object = jsonArray.getJSONObject(i);
					if (object.has("ID")) {
						map.put("ID", object.getString("ID"));
					} else {
						Log.d(TAG, "Not exist this key! ID");
					}
					if (object.has("MOBILENAME")) {
						map.put("MOBILENAME", object.getString("MOBILENAME"));
					} else {
						Log.d(TAG, "Not exist this key! MOBILENAME");
					}
					if (object.has("GPS_TIME")) {
						String temp = object.getString("GPS_TIME");
						if (null != temp && temp.length() > 0) {
							temp = getDateFromTime(temp);
						} else {
							temp = "";
						}
						map.put("GPS_TIME", temp);
					} else {
						Log.d(TAG, "Not exist this key! GPS_TIME");
					}
					if (object.has("LATITUDE")) {
						map.put("LATITUDE", object.getString("LATITUDE"));
					} else {
						Log.d(TAG, "Not exist this key! LATITUDE");
					}
					if (object.has("LONGITUDE")) {
						map.put("LONGITUDE", object.getString("LONGITUDE"));
					} else {
						Log.d(TAG, "Not exist this key! LONGITUDE");
					}
					if (object.has("SPEED")) {
						map.put("SPEED", object.getString("SPEED"));
					} else {
						Log.d(TAG, "Not exist this key! SPEED");
					}
					if (object.has("UPDATEDATE")) {
						String temp = object.getString("UPDATEDATE");
						if (null != temp && temp.contains("(")
								&& temp.contains("+")) {
							temp = temp.substring(temp.indexOf("(") + 1,
									temp.indexOf("+"));
							temp = getDateFromTime(temp);
						} else {
							temp = "";
						}
						map.put("UPDATEDATE", temp);
					} else {
						Log.d(TAG, "Not exist this key! t_signed_date");
					}
					if (object.has("ORIGIN")) {
						map.put("ORIGIN", object.getString("ORIGIN"));
					} else {
						Log.d(TAG, "Not exist this key! ORIGIN");
					}
					list.add(map);
				}
			} else {
				return null;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/*
	 * ����洬�����ػ�ȡ�洬��γ����Ϣ
	 */
	public ArrayList<HashMap<String, String>> getGPSByDistrict(String result) {

		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

		JSONObject jsonObject;
		JSONArray jsonArray;
		try {
			jsonObject = new JSONObject(result);
			String state = null;
			if (jsonObject.has("STATE")) {
				state = jsonObject.getString("STATE");
			}

			if ("1".equalsIgnoreCase(state) && jsonObject.has("SHIP_INFO")) {

				jsonArray = jsonObject.getJSONArray("SHIP_INFO");
				for (int i = 0; i < jsonArray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject object = jsonArray.getJSONObject(i);
					if (object.has("ID")) {
						map.put("ID", object.getString("ID"));
					} else {
						Log.d(TAG, "Not exist this key! ID");
					}
					if (object.has("MOBILENAME")) {
						map.put("MOBILENAME", object.getString("MOBILENAME"));
					} else {
						Log.d(TAG, "Not exist this key! MOBILENAME");
					}
					if (object.has("SHIP_BUSINESS_TYPE")) {
						map.put("SHIP_BUSINESS_TYPE",
								object.getString("SHIP_BUSINESS_TYPE"));
					} else {
						Log.d(TAG, "Not exist this key! SHIP_BUSINESS_TYPE");
					}

					if (object.has("GPS_TIME")) {
						String temp = object.getString("GPS_TIME");
						if (null != temp && temp.length() > 0) {
							temp = getDateFromTime(temp);
						} else {
							temp = "";
						}
						map.put("GPS_TIME", temp);
					} else {
						Log.d(TAG, "Not exist this key! GPS_TIME");
					}
					if (object.has("LATITUDE")) {
						map.put("LATITUDE", object.getString("LATITUDE"));
					} else {
						Log.d(TAG, "Not exist this key! LATITUDE");
					}
					if (object.has("LONGITUDE")) {
						map.put("LONGITUDE", object.getString("LONGITUDE"));
					} else {
						Log.d(TAG, "Not exist this key! LONGITUDE");
					}
					if (object.has("SPEED")) {
						map.put("SPEED", object.getString("SPEED"));
					} else {
						Log.d(TAG, "Not exist this key! SPEED");
					}
					if (object.has("UPDATEDATE")) {
						String temp = object.getString("UPDATEDATE");
						if (null != temp && temp.contains("(")
								&& temp.contains("+")) {
							temp = temp.substring(temp.indexOf("(") + 1,
									temp.indexOf("+"));
							temp = getDateFromTime(temp);
						} else {
							temp = "";
						}
						map.put("UPDATEDATE", temp);
					} else {
						Log.d(TAG, "Not exist this key! t_signed_date");
					}
					if (object.has("ORIGIN")) {
						map.put("ORIGIN", object.getString("ORIGIN"));
					} else {
						Log.d(TAG, "Not exist this key! ORIGIN");
					}
					list.add(map);
				}
			} else {
				return null;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// ����洬�����ȡ�洬��γ����Ϣ
	public ArrayList<HashMap<String, String>> getGPSByShipNo(String result) {

		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

		JSONObject jsonObject;
		JSONArray jsonArray;
		try {
			jsonObject = new JSONObject(result);
			String state = null;
			if (jsonObject.has("STATE")) {
				state = jsonObject.getString("STATE");
			}

			if ("1".equalsIgnoreCase(state) && jsonObject.has("SHIP_INFO")) {

				jsonArray = jsonObject.getJSONArray("SHIP_INFO");
				for (int i = 0; i < jsonArray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject object = jsonArray.getJSONObject(i);
					if (object.has("ID")) {
						map.put("ID", object.getString("ID"));
					} else {
						Log.d(TAG, "Not exist this key! ID");
					}
					if (object.has("MOBILENAME")) {
						map.put("MOBILENAME", object.getString("MOBILENAME"));
					} else {
						Log.d(TAG, "Not exist this key! MOBILENAME");
					}
					if (object.has("GPS_TIME")) {
						String temp = object.getString("GPS_TIME");
						if (null != temp && temp.length() > 0) {
							temp = getDateFromTime(temp);
						} else {
							temp = "";
						}
						map.put("GPS_TIME", temp);
					} else {
						Log.d(TAG, "Not exist this key! GPS_TIME");
					}
					if (object.has("LATITUDE")) {
						map.put("LATITUDE", object.getString("LATITUDE"));
					} else {
						Log.d(TAG, "Not exist this key! LATITUDE");
					}
					if (object.has("LONGITUDE")) {
						map.put("LONGITUDE", object.getString("LONGITUDE"));
					} else {
						Log.d(TAG, "Not exist this key! LONGITUDE");
					}
					if (object.has("SPEED")) {
						map.put("SPEED", object.getString("SPEED"));
					} else {
						Log.d(TAG, "Not exist this key! SPEED");
					}
					if (object.has("UPDATEDATE")) {
						String temp = object.getString("UPDATEDATE");
						if (null != temp && temp.contains("(")
								&& temp.contains("+")) {
							temp = temp.substring(temp.indexOf("(") + 1,
									temp.indexOf("+"));
							temp = getDateFromTime(temp);
						} else {
							temp = "";
						}
						map.put("UPDATEDATE", temp);
					} else {
						Log.d(TAG, "Not exist this key! t_signed_date");
					}
					if (object.has("ORIGIN")) {
						map.put("ORIGIN", object.getString("ORIGIN"));
					} else {
						Log.d(TAG, "Not exist this key! ORIGIN");
					}
					list.add(map);
				}
			} else {
				return null;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public HashMap<String, Object> getChangePasswordResult(String reslut) {

		// Log.d(TAG, "=====" + reslut);
		Boolean success = false;

		HashMap<String, Object> hashMap = null;

		try {
			JSONObject jsonObject = new JSONObject(reslut);

			hashMap = new HashMap<String, Object>();
			if (jsonObject.has("success")) {

				success = jsonObject.getBoolean("success");

			} else {
				success = false;
				Log.d(TAG, "The input data has not contain \"success\" key");
			}

			hashMap.put("success", success);
			String text = null;
			if (jsonObject.has("text")) {
				text = jsonObject.getString("text");
			} else {
				text = "fail";
				Log.d(TAG, "The input data has not contain \"text\" key");
			}
			hashMap.put("text", text);
			Log.d(TAG, "Login text:" + text + " ;");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return hashMap;
	}

	// ��ȡ������۾�γ����Ϣ
	public ArrayList<HashMap<String, String>> getAllPortGPS(String result) {

		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

		JSONObject jsonObject;
		JSONArray jsonArray;
		try {
			jsonObject = new JSONObject(result);
			String state = null;
			if (jsonObject.has("STATE")) {
				state = jsonObject.getString("STATE");
			}

			if ("1".equalsIgnoreCase(state) && jsonObject.has("SHIP_INFO")) {

				jsonArray = jsonObject.getJSONArray("SHIP_INFO");
				for (int i = 0; i < jsonArray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject object = jsonArray.getJSONObject(i);
					if (object.has("port_name")) {
						map.put("port_name", object.getString("port_name"));
					} else {
						Log.d(TAG, "Not exist this key! port_name");
					}
					if (object.has("longitude")) {
						map.put("longitude", object.getString("longitude"));
					} else {
						Log.d(TAG, "Not exist this key! longitude");
					}
					if (object.has("latitude")) {
						map.put("latitude", object.getString("latitude"));
					} else {
						Log.d(TAG, "Not exist this key! latitude");
					}
					list.add(map);
				}
			} else {
				return null;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/*
	 * ��ȡ�洬������Ŀ
	 */
	public ArrayList<HashMap<String, String>> getShipDetectItems(String result) {

		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

		JSONObject jsonObject;
		JSONArray jsonArray;
		try {
			jsonObject = new JSONObject(result);
			String state = null;

			jsonArray = jsonObject.getJSONArray("RECORDS");

			for (int i = 0; i < jsonArray.length(); i++) {
				HashMap<String, String> map = new HashMap<String, String>();
				JSONObject object = jsonArray.getJSONObject(i);
				if (object.has("id")) {
					map.put("id", object.getString("id"));
				} else {
					Log.d(TAG, "Not exist this key! id");
				}
				if (object.has("pid")) {
					map.put("pid", object.getString("pid"));
				} else {
					Log.d(TAG, "Not exist this key! pid");
				}
				if (object.has("name")) {
					map.put("name", object.getString("name"));
				} else {
					Log.d(TAG, "Not exist this key! name");
				}
				if (object.has("have_child")) {
					map.put("have_child", object.getString("have_child"));
				} else {
					Log.d(TAG, "Not exist this key! LONGITUDE");
				}
				if (object.has("have_image")) {
					map.put("have_image", object.getString("have_image"));
				} else {
					Log.d(TAG, "Not exist this key! have_image");
				}

				if (object.has("required")) {
					map.put("required", object.getString("required"));
				} else {
					Log.d(TAG, "Not exist this key! required");
				}
				if (object.has("ship_type")) {
					map.put("ship_type", object.getString("ship_type"));
				} else {
					Log.d(TAG, "Not exist this key! ship_type");
				}
				if (object.has("detect_type")) {
					map.put("detect_type", object.getString("detect_type"));
				} else {
					Log.d(TAG, "Not exist this key! detect_type");
				}
				if (object.has("standard")) {
					map.put("standard", object.getString("standard"));
				} else {
					Log.d(TAG, "Not exist this key! standard");
				}
				list.add(map);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/*
	 * ��ȡ�洬������Ŀ
	 */
	public ArrayList<HashMap<String, String>> getShipDetectItemsToServer(
			String result) {

		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

		JSONObject jsonObject;
		JSONArray jsonArray;
		try {
			jsonObject = new JSONObject(result);
			String state = null;
			if (jsonObject.has("STATE")
					&& "1".equalsIgnoreCase(jsonObject.getString("STATE"))) {

				jsonArray = jsonObject.getJSONArray("SHIP_INFO");

				for (int i = 0; i < jsonArray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject object = jsonArray.getJSONObject(i);
					if (object.has("id")) {
						map.put("id", object.getString("id"));
					} else {
						Log.d(TAG, "Not exist this key! id");
					}
					if (object.has("itemid")) {
						map.put("itemid", object.getString("itemid"));
					} else {
						Log.d(TAG, "Not exist this key! itemid");
					}
					if (object.has("itempid")) {
						map.put("itempid", object.getString("itempid"));
					} else {
						Log.d(TAG, "Not exist this key! itempid");
					}
					if (object.has("name")) {
						map.put("name", object.getString("name"));
					} else {
						Log.d(TAG, "Not exist this key! name");
					}
					if (object.has("have_child")) {
						map.put("have_child", object.getString("have_child"));
					} else {
						Log.d(TAG, "Not exist this key! LONGITUDE");
					}
					if (object.has("have_image")) {
						map.put("have_image", object.getString("have_image"));
					} else {
						Log.d(TAG, "Not exist this key! have_image");
					}

					if (object.has("required")) {
						map.put("required", object.getString("required"));
					} else {
						Log.d(TAG, "Not exist this key! required");
					}
					if (object.has("ship_type")) {
						map.put("ship_type", object.getString("ship_type"));
					} else {
						Log.d(TAG, "Not exist this key! ship_type");
					}
					if (object.has("detect_type")) {
						map.put("detect_type", object.getString("detect_type"));
					} else {
						Log.d(TAG, "Not exist this key! detect_type");
					}
					if (object.has("standard")) {
						map.put("standard", object.getString("standard"));
					} else {
						Log.d(TAG, "Not exist this key! standard");
					}
					if (object.has("approval")) {
						map.put("approval", object.getString("approval"));
					} else {
						Log.d(TAG, "Not exist this key! approval");
					}
					if (object.has("problem")) {
						map.put("problem", object.getString("problem"));
					} else {
						Log.d(TAG, "Not exist this key! problem");
					}
					if (object.has("attachment")) {
						map.put("attachment", object.getString("attachment"));
					} else {
						Log.d(TAG, "Not exist this key! attachment");
					}
					list.add(map);
				}
			} else
				list = null;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// ��ȡ�洬���������¼
	public ArrayList<HashMap<String, String>> getShipDetectApplyInfo(
			String result) {

		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

		JSONObject jsonObject;
		JSONArray jsonArray;
		try {
			jsonObject = new JSONObject(result);
			String state = null;
			if (jsonObject.has("STATE")) {
				state = jsonObject.getString("STATE");
			}

			if ("1".equalsIgnoreCase(state) && jsonObject.has("SHIP_INFO")) {

				jsonArray = jsonObject.getJSONArray("SHIP_INFO");
				for (int i = 0; i < jsonArray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject object = jsonArray.getJSONObject(i);
					if (object.has("id")) {
						map.put("id", object.getString("id"));
					} else {
						Log.d(TAG, "Not exist this key! id");
					}
					if (object.has("apply_no")) {
						map.put("apply_no", object.getString("apply_no"));
					} else {
						Log.d(TAG, "Not exist this key! apply_no");
					}
					if (object.has("ship_name")) {
						map.put("ship_name", object.getString("ship_name"));
					} else {
						Log.d(TAG, "Not exist this key! ship_name");
					}
					if (object.has("ship_length")) {
						map.put("ship_length", object.getString("ship_length"));
					} else {
						Log.d(TAG, "Not exist this key! ship_length");
					}
					if (object.has("ship_material")) {
						map.put("ship_material",
								object.getString("ship_material"));
					} else {
						Log.d(TAG, "Not exist this key! ship_material");
					}
					if (object.has("ship_no")) {
						map.put("ship_no", object.getString("ship_no"));
					} else {
						Log.d(TAG, "Not exist this key! ship_no");
					}
					if (object.has("ship_port")) {
						map.put("ship_port", object.getString("ship_port"));
					} else {
						Log.d(TAG, "Not exist this key! ship_port");
					}
					if (object.has("ship_tot_power")) {
						map.put("ship_tot_power",
								object.getString("ship_tot_power"));
					} else {
						Log.d(TAG, "Not exist this key! ship_tot_power");
					}
					if (object.has("app_detect_type")) {
						map.put("app_detect_type",
								object.getString("app_detect_type"));
					} else {
						Log.d(TAG, "Not exist this key! app_detect_type");
					}
					if (object.has("working_area")) {
						map.put("working_area",
								object.getString("working_area"));
					} else {
						Log.d(TAG, "Not exist this key! working_area");
					}
					if (object.has("detect_location")) {
						map.put("detect_location",
								object.getString("detect_location"));
					} else {
						Log.d(TAG, "Not exist this key! detect_location");
					}
					if (object.has("detect_location_type")) {
						map.put("detect_location_type",
								object.getString("detect_location_type"));
					} else {
						Log.d(TAG, "Not exist this key! detect_location_type");
					}
					if (object.has("ship_owner")) {
						map.put("ship_owner", object.getString("ship_owner"));
					} else {
						Log.d(TAG, "Not exist this key! ship_owner");
					}
					if (object.has("attachment_type")) {
						map.put("attachment_type",
								object.getString("attachment_type"));
					} else {
						Log.d(TAG, "Not exist this key! attachment_type");
					}
					if (object.has("attachment")) {
						map.put("attachment", object.getString("attachment"));
					} else {
						Log.d(TAG, "Not exist this key! attachment");
					}
					if (object.has("app_date")) {
						map.put("app_date",
								getDateFromTime(
										object.getString("app_date").substring(
												6, 20)).substring(0, 11));
					} else {
						Log.d(TAG, "Not exist this key! app_date");
					}
					if (object.has("app_tel")) {
						map.put("app_tel",

						object.getString("app_tel"));
					} else {
						Log.d(TAG, "Not exist this key! app_tel");
					}
					if (object.has("app_name")) {
						map.put("app_name", object.getString("app_name"));
					} else {
						Log.d(TAG, "Not exist this key! app_name");
					}
					if (object.has("app_account")) {
						map.put("app_account", object.getString("app_account"));
					} else {
						Log.d(TAG, "Not exist this key! app_account");
					}
					if (object.has("app_explanation")) {
						map.put("app_explanation",
								object.getString("app_explanation"));
					} else {
						Log.d(TAG, "Not exist this key! app_explanation");
					}
					if (object.has("plan_repair_date")) {
						map.put("plan_repair_date",
								object.getString("plan_repair_date"));
					} else {
						Log.d(TAG, "Not exist this key! plan_repair_date");
					}
					if (object.has("plan_detect_date")) {
						map.put("plan_detect_date",
								object.getString("plan_detect_date"));
					} else {
						Log.d(TAG, "Not exist this key! plan_detect_date");
					}
					if (object.has("complete_detect_date")) {
						map.put("complete_detect_date",
								object.getString("complete_detect_date"));
					} else {
						Log.d(TAG, "Not exist this key! complete_detect_date");
					}
					if (object.has("detect_ins")) {
						map.put("detect_ins", object.getString("detect_ins"));
					} else {
						Log.d(TAG, "Not exist this key! detect_ins");
					}
					if (object.has("state")) {
						map.put("state", object.getString("state"));
					} else {
						Log.d(TAG, "Not exist this key! state");
					}
					if (object.has("back_reason")) {
						map.put("back_reason", object.getString("back_reason"));
					} else {
						Log.d(TAG, "Not exist this key! back_reason");
					}
					if (object.has("working_area")) {
						map.put("working_area",
								object.getString("working_area"));
					} else {
						Log.d(TAG, "Not exist this key! working_area");
					}
					if (object.has("verifyenr_no")) {
						map.put("verifyenr_no",
								object.getString("verifyenr_no"));
					} else {
						Log.d(TAG, "Not exist this key! verifyenr_no");
					}
					if (object.has("n")) {
						map.put("n", object.getString("n"));
					} else {
						Log.d(TAG, "Not exist this key! n");
					}
					if (object.has("id1")) {
						map.put("id1", object.getString("id1"));
					} else {
						Log.d(TAG, "Not exist this key! id1");
					}
					list.add(map);
				}

			} else {
				return null;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// ��ȡָ�����洬���������¼
	public ArrayList<HashMap<String, String>> getSpcShipDetectApplyInfo(
			String result, String shipName) {

		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

		JSONObject jsonObject;
		JSONArray jsonArray;
		try {
			jsonObject = new JSONObject(result);
			String state = null;
			if (jsonObject.has("STATE")) {
				state = jsonObject.getString("STATE");
			}

			if ("1".equalsIgnoreCase(state) && jsonObject.has("SHIP_INFO")) {

				jsonArray = jsonObject.getJSONArray("SHIP_INFO");
				for (int i = 0; i < jsonArray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject object = jsonArray.getJSONObject(i);

					if (object.has("ship_name")
							&& shipName.equals(object.getString("ship_name"))) {
						map.put("ship_name", object.getString("ship_name"));
						if (object.has("id")) {
							map.put("id", object.getString("id"));
						} else {
							Log.d(TAG, "Not exist this key! id");
						}
						if (object.has("apply_no")) {
							map.put("apply_no", object.getString("apply_no"));
						} else {
							Log.d(TAG, "Not exist this key! apply_no");
						}
						if (object.has("ship_no")) {
							map.put("ship_no", object.getString("ship_no"));
						} else {
							Log.d(TAG, "Not exist this key! ship_no");
						}
						if (object.has("ship_length")) {
							map.put("ship_length",
									object.getString("ship_length"));
						} else {
							Log.d(TAG, "Not exist this key! ship_length");
						}
						if (object.has("verifyenr_no")) {
							map.put("verifyenr_no",
									object.getString("verifyenr_no"));
						} else {
							Log.d(TAG, "Not exist this key! verifyenr_no");
						}
						if (object.has("ship_port")) {
							map.put("ship_port", object.getString("ship_port"));
						} else {
							Log.d(TAG, "Not exist this key! ship_port");
						}
						if (object.has("working_area")) {
							map.put("working_area",
									object.getString("working_area"));
						} else {
							Log.d(TAG, "Not exist this key! working_area");
						}
						if (object.has("detect_location")) {
							map.put("detect_location",
									object.getString("detect_location"));
						} else {
							Log.d(TAG, "Not exist this key! detect_location");
						}
						if (object.has("app_detect_type")) {
							map.put("app_detect_type",
									object.getString("app_detect_type"));
						} else {
							Log.d(TAG, "Not exist this key! app_detect_type");
						}
						if (object.has("app_name")) {
							map.put("app_name", object.getString("app_name"));
						} else {
							Log.d(TAG, "Not exist this key! app_name");
						}

						if (object.has("app_date")) {
							map.put("app_date",
									getDateFromTime(
											object.getString("app_date")
													.substring(6, 20))
											.substring(0, 11));
						} else {
							Log.d(TAG, "Not exist this key! app_date");
						}
						if (object.has("app_tel")) {
							map.put("app_tel", object.getString("app_tel"));
						} else {
							Log.d(TAG, "Not exist this key! app_tel");
						}

						if (object.has("attachment_type")) {
							map.put("attachment_type",
									object.getString("attachment_type"));
						} else {
							Log.d(TAG, "Not exist this key! attachment_type");
						}
						if (object.has("attachment")) {
							map.put("attachment",
									object.getString("attachment"));
						} else {
							Log.d(TAG, "Not exist this key! attachment");
						}
						if (object.has("detect_ins")) {
							map.put("detect_ins",
									object.getString("detect_ins"));
						} else {
							Log.d(TAG, "Not exist this key! detect_ins");
						}

						if (object.has("state")) {
							map.put("state", object.getString("state"));
						} else {
							Log.d(TAG, "Not exist this key! state");
						}
						if (object.has("n")) {
							map.put("n", object.getString("n"));
						} else {
							Log.d(TAG, "Not exist this key! n");
						}
						if (object.has("id1")) {
							map.put("id1", object.getString("id1"));
						} else {
							Log.d(TAG, "Not exist this key! id1");
						}
						list.add(map);
						break;
					} else {
						Log.d(TAG, "Not exist this key! ship_name");
					}

				}

			} else {
				return null;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// �õ��������̽��

	public HashMap<String, String> getStartProcessResult(String result) {

		HashMap<String, String> map = null;
		JSONObject jsonObject, childobject;

		try {
			jsonObject = new JSONObject(result);
			if (jsonObject.has("Attributes")) {
				childobject = jsonObject.getJSONObject("Attributes");

				map = new HashMap<String, String>();

				if (childobject.has("success")) {
					map.put("success", childobject.getBoolean("success") + "");
				} else {
					Log.d(TAG, "Not exist this key! success");
				}

				if (childobject.has("message")) {
					map.put("message", childobject.getString("message"));
				} else {
					Log.d(TAG, "Not exist this key! message");
				}
			} else {
				return null;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return map;
	}

	// �����¼
	public ArrayList<HashMap<String, String>> getShipDetectRecord(String result) {

		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

		JSONObject jsonObject;
		JSONArray jsonArray;
		try {
			jsonObject = new JSONObject(result);
			String state = null;
			if (jsonObject.has("STATE")) {
				state = jsonObject.getString("STATE");
			}

			if ("1".equalsIgnoreCase(state) && jsonObject.has("SHIP_INFO")) {

				jsonArray = jsonObject.getJSONArray("SHIP_INFO");
				for (int i = 0; i < jsonArray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject object = jsonArray.getJSONObject(i);
					if (object.has("id")) {
						map.put("id", object.getString("id"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("ship_name")) {
						map.put("ship_name", object.getString("ship_name"));
					} else {
						Log.d(TAG, "Not exist this key! ship_name");
					}
					if (object.has("app_name")) {
						map.put("app_name", object.getString("app_name"));
					} else {
						Log.d(TAG, "Not exist this key! app_name");
					}
					if (object.has("app_date")) {
						String temp = object.getString("app_date");
						if (null != temp && temp.contains("(")
								&& temp.contains("+")) {
							temp = temp.substring(temp.indexOf("(") + 1,
									temp.indexOf("+"));
							temp = getDateFromTime(temp).substring(0, 11);
						} else {
							temp = "";
						}
						map.put("app_date", temp);
					} else {
						Log.d(TAG, "Not exist this key! app_date");
					}
					list.add(map);
				}
			} else {
				return null;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// ��ͼ��¼
	public ArrayList<HashMap<String, String>> getDrawingCheckRecord(
			String result) {

		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

		JSONObject jsonObject;
		JSONArray jsonArray;
		try {
			jsonObject = new JSONObject(result);
			String state = null;
			if (jsonObject.has("STATE")) {
				state = jsonObject.getString("STATE");
			}

			if ("1".equalsIgnoreCase(state) && jsonObject.has("SHIP_INFO")) {

				jsonArray = jsonObject.getJSONArray("SHIP_INFO");
				for (int i = 0; i < jsonArray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject object = jsonArray.getJSONObject(i);
					if (object.has("id")) {
						map.put("id", object.getString("id"));
					} else {
						Log.d(TAG, "Not exist this key!");
					}
					if (object.has("drawing_num")) {
						map.put("drawing_num", object.getString("drawing_num"));
					} else {
						Log.d(TAG, "Not exist this key! drawing_num");
					}
					if (object.has("app_name")) {
						map.put("app_name", object.getString("app_name"));
					} else {
						Log.d(TAG, "Not exist this key! app_name");
					}
					if (object.has("app_date")) {
						String temp = object.getString("app_date");
						if (null != temp && temp.contains("(")
								&& temp.contains("+")) {
							temp = temp.substring(temp.indexOf("(") + 1,
									temp.indexOf("+"));
							temp = getDateFromTime(temp).substring(0, 11);
						} else {
							temp = "";
						}
						map.put("app_date", temp);
					} else {
						Log.d(TAG, "Not exist this key! app_date");
					}
					list.add(map);
				}
			} else {
				return null;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}
