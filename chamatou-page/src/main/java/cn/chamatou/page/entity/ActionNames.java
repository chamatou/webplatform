package cn.chamatou.page.entity;
/**
 * 显示图片名称
 */
public enum ActionNames {
	USER_LOGIN_IMAGE {
		@Override
		public String toString() {
			return "用户版登陆页图片";
		}
	},
	BIZ_LOGIN_IMAGE {
		@Override
		public String toString() {
			return "商户登陆页图片";
		}
	},
	USER_LOGIN_IMAGE_RESULT_SET {
		@Override
		public String toString() {
			return "用户版登陆页图片集";
		}
	},
	BIZ_LOGIN_IMAGE_RESULT_SET {
		@Override
		public String toString() {
			return "商户登陆页图片集";
		}
	}
}