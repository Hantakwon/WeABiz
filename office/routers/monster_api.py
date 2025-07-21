from fastapi import APIRouter

router = APIRouter()

# 간단한 캐릭터 데이터 예시
enemies_data = {
    "snail": {
        "name": "달팽이",
        "hp": 3,
        "exp" : 1,
        "speed": "0.2",
        "size" : [20, 20],
        "image": "http://127.0.0.1:8000/static/images/monster/snail.gif", 
        "description": "느리고 약한 몬스터입니다.",
    },
    "slime": {
        "name": "슬라임",
        "hp": 6,
        "exp" : 2,
        "speed": "0.6",
        "size" : [30, 50],
        "image": "http://127.0.0.1:8000/static/images/monster/slime.gif", 
        "description": "쉽게 잡을 수 있는 몬스터입니다.",
    },
    "mushroom": {
        "name": "주황버섯",
        "hp": 10,
        "exp" : 3,
        "speed": "0.4",
        "size" : [30, 30],
        "image": "http://127.0.0.1:8000/static/images/monster/mushroom.gif", 
        "description": "숲에서 자주 발견됩니다.",
    },
    "yeti": {
        "name": "예티",
        "hp": 15,
        "exp" : 5,
        "speed": "1",
        "size" : [60, 60],
        "image": "http://127.0.0.1:8000/static/images/monster/yeti.gif", 
        "description": "설원에 사는 강한 몬스터입니다.",
    },
    "golem": {
        "name": "골렘",
        "hp": 20,
        "exp" : 6,
        "speed": "0.8",
        "size" : [80, 80],
        "image": "http://127.0.0.1:8000/static/images/monster/golem.gif", 
        "description": "단단한 몸을 가진 몬스터입니다.",
    },
    "balrog": {
        "name": "발록",
        "hp": 25,
        "exp" : 10,
        "speed": "1.4",
        "size" : [100, 100],
        "image": "http://127.0.0.1:8000/static/images/monster/balrog.gif", 
        "description": "가장 강력한 몬스터입니다.",
    }
}

@router.get("/info", response_model=dict)
async def get_enemies():
    return enemies_data

