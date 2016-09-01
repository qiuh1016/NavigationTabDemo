package com.cetcme.zytyumin.jecInfoHttp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.util.Log;

public class ParseJson {

	private final String TAG = "ParseJson";

	public ParseJson() {
		super();
	}

	// 船检记录
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

	// 审图记录
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

	// 投保基本信息
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
							tmp = "新保";
						} else if ("1".equalsIgnoreCase(tmp)) {
							tmp = "续保";
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
							temp = "否";
						} else if ("1".equals(temp)) {
							temp = "是";
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

	// 投保详细信息
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
						temp = "否";
					} else if ("1".equals(temp)) {
						temp = "是";
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
						tmp = "新保";
					} else if ("1".equalsIgnoreCase(tmp)) {
						tmp = "续保";
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

	// 理赔基本信息
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

	// 理赔详细信息
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

	// 渔船交易基本信息
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
							temp = "正常";
						} else if ("1".equals(temp)) {
							temp = "删除";
						} else if ("2".equals(temp)) {
							temp = "报废";
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
							temp = "直接投入作业生产";
						} else if ("1".equals(temp)) {
							temp = "更新改造";
						} else if ("2".equals(temp)) {
							temp = "拆解";
						} else if ("3".equals(temp)) {
							temp = "其它";
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
							temp = "正常";
						} else if ("1".equals(temp)) {
							temp = "删除";
						} else if ("2".equals(temp)) {
							temp = "报废";
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

	// 渔船交易详细信息
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
						temp = "直接投入作业生产";
					} else if ("1".equals(temp)) {
						temp = "更新改造";
					} else if ("2".equals(temp)) {
						temp = "拆解";
					} else if ("3".equals(temp)) {
						temp = "其它";
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

}
