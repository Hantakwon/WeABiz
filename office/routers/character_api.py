from fastapi import APIRouter

router = APIRouter()

characters_data = {
    "전사": {
        "name": "전사",
        "image": "http://127.0.0.1:8000/static/images/warrior.png",
        "description": "체력이 많은 직업입니다.",
        "detail": "속도는 느리지만 체력이 많아 후반에 유리합니다."
    },
    "궁수": {
        "name": "궁수",
        "image": "http://127.0.0.1:8000/static/images/archer.png",
        "description": "밸런스 잡힌 직업입니다.",
        "detail": "속도와 공격 모두 균형있는 직업입니다."
    }
    ,
    "도적": {
        "name": "도적",
        "image": "http://127.0.0.1:8000/static/images/thief.png",
        "description": "가장 빠른 직업입니다.",
        "detail": "모든 직업 중 가장 빨라 초반에 유리합니다."
    }
    ,
    "법사": {
        "name": "법사",
        "image": "http://127.0.0.1:8000/static/images/magician.png",
        "description": "가장 강력한 법사입니다.",
        "detail": "데미지가 강력해 초반이 편합니다. 다만 체력이 적습니다."
    }
}

@router.get("/info", response_model=dict)
async def get_character():
    return characters_data

warrior = {
    "hp" : "5",
    "atk" : "2",
    "speed" : "1",
    "skill" : ["",""],
    "delay" : 700,
    "stand_img" : "http://127.0.0.1:8000/static/images/warrior_stand.gif",
    "walk_img" : "http://127.0.0.1:8000/static/images/warrior_walk.gif",
    "atk_img" : "http://127.0.0.1:8000/static/images/warrior_atk.gif",
    "imagesize" : [163,155],
    "hitbox": {
        "width": 26,
        "height": 52,
        "center_offset": {
            "x": 81,
            "y": 52
        }
    }
}

archer = {
    "hp" : "3",
    "atk" : "3",
    "speed" : "2",
    "skill" : ["",""],
    "delay" : 700,
    "stand_img" : "http://127.0.0.1:8000/static/images/archer_stand.gif",
    "walk_img" : "http://127.0.0.1:8000/static/images/archer_walk.gif",
    "atk_img" : "http://127.0.0.1:8000/static/images/archer_atk.gif",
    "imagesize" : [186,137],
    "hitbox": {
        "width": 26,
        "height": 52,
        "center_offset": {
            "x": 87,
            "y": 42
        }
    }   
}

magician = {
    "hp" : "2",
    "atk" : "5",
    "speed" : "2",
    "skill" : ["",""],
    "delay" : 700,
    "stand_img" : "http://127.0.0.1:8000/static/images/magician_stand.gif",
    "walk_img" : "http://127.0.0.1:8000/static/images/magician_walk.gif",
    "atk_img" : "http://127.0.0.1:8000/static/images/magician_atk.gif",
    "heal_img" : "http://127.0.0.1:8000/static/images/magician_heal.gif",
    "imagesize" : [150,140],
    "hitbox": {
        "width": 26,
        "height": 52,
        "center_offset": {
            "x": 62,
            "y": 62
        }
    }   
}

thief = {
    "hp" : "3",
    "atk" : "3",
    "speed" : "3",
    "skill" : ["",""],
    "delay" : 700,
    "stand_img" : "http://127.0.0.1:8000/static/images/thief_stand.gif",
    "walk_img" : "http://127.0.0.1:8000/static/images/thief_walk.gif",
    "atk_img" : "http://127.0.0.1:8000/static/images/thief_atk.gif",
    "imagesize" : [175,89],
    "hitbox": {
        "width": 26,
        "height": 52,
        "center_offset": {
            "x": 95,
            "y": 24
        }
    }   
}

@router.get("/status/{name}")
async def get_status(name: str):
    if name=='전사':
        return warrior
    elif name=='궁수':
        return archer
    elif name=='법사':
        return magician
    elif name=='도적':
        return thief
    
@router.get("/hello")
async def hello():
    return {
        "message" : "hello"
    }