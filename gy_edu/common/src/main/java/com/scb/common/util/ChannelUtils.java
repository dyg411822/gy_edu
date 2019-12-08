package com.scb.common.util;

import net.polyv.live.bean.request.channel.PLChannelBasicCreateBody;
import net.polyv.live.bean.request.channel.PLChannelBasicCreateRequest;
import net.polyv.live.bean.request.channel.PLChannelBasicSettingCreateBody;
import net.polyv.live.bean.request.channel.PLChannelBasicSettingUpdateBody;
import net.polyv.live.bean.request.channel.PLChannelBasicUpdateBody;
import net.polyv.live.bean.request.channel.PLChannelBasicUpdateRequest;
import net.polyv.live.bean.request.channel.PLChannelCreateRequest;
import net.polyv.live.bean.request.channel.PLChannelDeleteRequest;
import net.polyv.live.bean.request.channel.PLChannelMaxViewerSetRequest;
import net.polyv.live.bean.request.channel.PLChannelNameSetRequest;
import net.polyv.live.bean.request.channel.PLChannelPasswordSetRequest;
import net.polyv.live.bean.request.channel.PLChannelPublisherSetRequest;
import net.polyv.live.bean.result.channel.PLChannelBasicCreateResult;
import net.polyv.live.bean.result.channel.PLChannelCommonResult;
import net.polyv.live.bean.result.channel.PLChannelCreateResult;
import net.polyv.live.service.PLChannelBasicService;
import net.polyv.live.service.PLChannelService;
import net.polyv.live.service.impl.PLChannelBasicServiceImpl;
import net.polyv.live.service.impl.PLChannelServiceImpl;

/**
 * 点播工具类
 *
 */
public class ChannelUtils {
	private final static String AppID = "fdsbk6jeg4";

	private final static String AppSecret = "d2f37d29d21e4b478fb8992385866353";

	private final static String userId = "49f3437da6";
	
	/**
	 * 创建并初始化频道
	 * @param channelName
	 * @param channelPassword
	 * @param maxViewer
	 * @param startTime
	 * @param publisherName
	 * @return
	 */
	public static PLChannelBasicCreateResult CreateChannelBasic(String channelName,String channelPassword
			,Integer maxViewer,Long startTime,String publisherName) {
		// 创建请求参数json对象
		PLChannelBasicCreateBody body = new PLChannelBasicCreateBody();
		// 构建请求体json接口basicSetting
		PLChannelBasicSettingCreateBody basicSetting = new PLChannelBasicSettingCreateBody();
		// 设置频道名称
		basicSetting.setName(channelName);
		// 设置密码
		basicSetting.setChannelPasswd(channelPassword);
		// 设置观看人数
		basicSetting.setMaxViewer(maxViewer);
		// 设置开始时间，13位时间搓
		basicSetting.setStartTime(startTime);
		// 设置主持人昵称
		basicSetting.setPublisher(publisherName);
		// 加入到请求对象body
		body.setBasicSetting(basicSetting);
		// 构建创建频道并初始化请求request对象
		PLChannelBasicCreateRequest request = new PLChannelBasicCreateRequest(AppID, AppSecret,body);
		// 构建请求service，用户把请求数据发送到服务器
		PLChannelBasicService service = new PLChannelBasicServiceImpl();
		// 发送请求数据到服务器并返回数据
		PLChannelBasicCreateResult result = service.createChannel(request);
		//  成功响应
		return result;
	}

	/**
	 * 创建频道
	 * @return
	 */
	public static PLChannelCreateResult CreateChannel(String name ,String password) {
		//  创建频道的创建请求对象（并传入直播账号的APPID和APPSECRET）
		PLChannelCreateRequest request = new PLChannelCreateRequest(AppID, AppSecret);
		//  设置用户ID
		request.setUserId(userId);
		//  设置密码
		request.setChannelPasswd(password);
		//  设置名称
		request.setName(name);
		//  频道接口对象
		PLChannelService plChannelService = new PLChannelServiceImpl();
		//  调用频道接口对象的创建频道方法获取请求结果对象
		PLChannelCreateResult result = plChannelService.createChannel(request);
		return result;
	}
	/**
	 * 设置频道最大在线人数
	 * @param channelId 频道号 MaxViewer 最大在线数 userId 用户id
	 * @return
	 */
	public static PLChannelCommonResult  SetChannelMaxViewer(Integer channelId,String MaxViewer,String userId) {
	//  创建设置频道的最大在线人数请求对象（并传入直播账号的APPID和APPSECRET）
		PLChannelMaxViewerSetRequest request = new PLChannelMaxViewerSetRequest(AppID, AppSecret);
		//  设置用户ID
		request.setUserId(userId);
		//  设置最大在线人数
		request.setMaxViewer(MaxViewer);
		//  频道接口对象
		PLChannelService plChannelService = new PLChannelServiceImpl();
		//  调用频道接口对象的设置最大在线人数方法获取请求结果对象
		PLChannelCommonResult result = plChannelService.setChannelMaxViewer(channelId, request);
		//  成功响应
		return result;
	}
	/**
	 * 修改频道名称
	 * @param name
	 * @param channelId 频道号
	 * @return
	 */
	public static PLChannelCommonResult  ChangeChannelName(String name,Integer channelId) {
	//  创建设置频道的名称请求对象（并传入直播账号的APPID和APPSECRET）
		PLChannelNameSetRequest request = new PLChannelNameSetRequest(AppID, AppSecret);
		//  设置频道名称
		request.setName(name);
		//  频道接口对象
		PLChannelService plChannelService = new PLChannelServiceImpl();
		//  调用频道接口对象的设置频道名称方法获取请求结果对象
		PLChannelCommonResult result = plChannelService.setChannelName(channelId, request);
		//  成功响应
		return result;
	}
	/**
	 * 修改主持人姓名
	 * @param PublisherName主持人姓名
	 * @param channelId频道号
	 * @return
	 */
	public static PLChannelCommonResult ChangeChannelPublisher(String PublisherName,Integer channelId,String userId) {
	//  创建设置频道的主持人名称请求对象（并传入直播账号的APPID和APPSECRET）
		PLChannelPublisherSetRequest request = new PLChannelPublisherSetRequest(AppID, AppSecret);
		 //  设置频道号
		request.setChannelId(channelId);
		//   设置主持人名称
		request.setPublisher(PublisherName);
		//  频道接口对象
		PLChannelService plChannelService = new PLChannelServiceImpl();
		//  调用频道接口对象的设置主持人名称方法获取请求结果对象
		PLChannelCommonResult result = plChannelService.setChannelPublisher(userId, request);
		//  成功响应
		return result;
	}
	/**
	 * 删除频道号
	 * @param userId用户id
	 * @param channelId频道号
	 * @return
	 */
	public static PLChannelCommonResult DelChannel(String userId,Integer channelId) {
	//  创建删除频道请求对象（并传入直播账号的APPID和APPSECRET）
		PLChannelDeleteRequest request = new PLChannelDeleteRequest(AppID, AppSecret);
		 //  设置用户ID
		request.setUserId(userId);
		//  频道接口对象
		PLChannelService plChannelService = new PLChannelServiceImpl();
		//  调用频道接口对象的删除频道方法获取请求结果对象
		PLChannelCommonResult result = plChannelService.deleteChannel(channelId, request);
		//  成功响应
		return result;
	}
	/**
	 * 
	 * @param passWord密码
	 * @param channelId频道号
	 * @param userId用户id
	 * @return
	 */
	public static PLChannelCommonResult SetChannelPassword(String passWord,Integer channelId,String userId) {
	//  创建设置频道密码请求对象（并传入直播账号的APPID和APPSECRET）
		PLChannelPasswordSetRequest request = new PLChannelPasswordSetRequest(AppID, AppSecret);
		//   设置频道密码
		request.setPasswd(passWord);
		 //  设置频道号
		request.setChannelId(channelId);
		//  频道接口对象
		PLChannelService plChannelService = new PLChannelServiceImpl();
		//  调用频道接口对象的设置频道密码方法获取请求结果对象
		PLChannelCommonResult result = plChannelService.setChannelPassword(userId, request);
		//  成功响应
		return result;
	}
	/**
	 * 修改频道详细设置
	 * @param channelName频道名称
	 * @param startTimes 直播开始时间
	 * @param publisherName 主播
	 * @param channelId 频道号
	 * @return
	 */
	public static PLChannelCommonResult UpdateChannelBasic(String channelName,Long startTimes,String publisherName
			,Integer channelId) {
		// 更新频道请求参数json对象
		PLChannelBasicUpdateBody body = new PLChannelBasicUpdateBody();
		// 构建请求体json接口updateBody
		PLChannelBasicSettingUpdateBody updateBody = new PLChannelBasicSettingUpdateBody();
		// 设置频道名称
		updateBody.setName(channelName);
		// 设置直播开始时间
		updateBody.setStartTime(startTimes);
		// 设置频道主持人
		updateBody.setPublisher(publisherName);
		// 设置到请求体中
		body.setBasicSetting(updateBody);
		// 构建请求体中
		PLChannelBasicUpdateRequest request = new PLChannelBasicUpdateRequest(AppID, AppSecret, channelId, body);
		// 构建请求service，用户把请求数据发送到服务器
		PLChannelBasicService service = new PLChannelBasicServiceImpl();
		// 发送请求数据到服务器并返回数据
		PLChannelCommonResult channel = service.updateChannel(request);
		//  成功响应
		return channel;
	}
}
