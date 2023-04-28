-- 유저 더미데이터
--INSERT INTO `user` VALUE ('testUUID','KAKAO','ssafy@ssafy.com','ssafy1',NULL,now(),NULL,'',0,'A','ROLE_USER',0);

-- 하트 더미데이터
INSERT INTO `heart` (`name`, `image_url`, `short_description`, `long_description`, `type`, `acq_condition`) VALUES
	('호감', 'https://heart-ing.s3.ap-northeast-2.amazonaws.com/heart/heart_yellow_1.svg', '호감 하트 짧은 설명', '따스한 말투와 행동이 만나면 태어나는 하트. \n고마운 상대에게 보낼 경우, 상대의 마음에 봄바람을 불어 넣어 기분 좋은 하루를 만들어준다.', 'DEFAULT', '기본 제공'),
	('응원', 'https://heart-ing.s3.ap-northeast-2.amazonaws.com/heart/heart_blue_1.svg', '네가 꿈꾸던 세상에 갈 수 있을거야!', '두려움을 이길 용기를 전할 때 태어난 하트. \n너의 하늘을 날아봐! 더 높은 세상을 꿈꾸는 상대에게 날개를 달아준다.', 'DEFAULT', '기본 제공'),
	('우정', 'https://heart-ing.s3.ap-northeast-2.amazonaws.com/heart/heart_green_1.svg', '우정 하트 짧은 설명!', '서로의 소중한 추억이 쌓여 태어난 하트. \n청춘을 함께하고 있는 상대에게 보낼 경우, 소나무처럼 영원히 푸르를 우정을 약속한다.', 'DEFAULT', '기본 제공'),
	('설렘', 'https://heart-ing.s3.ap-northeast-2.amazonaws.com/heart/heart_pink_1.svg', '설렘 하트 짧은 설명!', '널 보면 심장이 두근두근! 발그레해진 볼에서 태어난 하트. \n내 마음을 간질이는 상대에게 보낼 경우,  상대에게 벅차오르는 설렘이  피어난다.', 'DEFAULT', '기본 제공'),
	('애정', 'https://heart-ing.s3.ap-northeast-2.amazonaws.com/heart/heart_red_1.svg', '혹시 나 너 좋아하냐?', '진실한 마음을 숨기지 못해 새어난 사랑의 하트. \n이 하트를 보내면 만발한 사랑의 향기를 맡을 수 있다.', 'DEFAULT', '기본 제공'),
	('행성', 'https://heart-ing.s3.ap-northeast-2.amazonaws.com/heart/heart_planet_1.svg', '우주에 단 하나 뿐인 너!', '오직 한 사람만 생각하는 마음이 커져 태어난 하트. \n당신의 중력으로 상대방을 서서히 끌어당긴다.', 'SPECIAL', '특정인에게 5회 이상 전송'),
	('무지개', 'https://heart-ing.s3.ap-northeast-2.amazonaws.com/heart/heart_rainbow_1.svg', '내 모든 마음을 담아서!', '상대방을 위한 여러 마음들이 모여 탄생한 무지개 하트. \n하트를 받은 상대의 마음도 찬란한 무지개빛으로 물들인다.', 'SPECIAL', '모든 기본 하트 전송');

-- 획득 하트 더미데이터
--INSERT INTO `user_heart` VALUES (1,'testUUID',6);