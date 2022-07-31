package self.nesl.komica_api.model

sealed class Board(val name: String, val url: String) {
    sealed class Sora(name: String, url: String) : Board(name, url) {
        object 綜合 : Sora("綜合", "https://sora.komica.org/00")
        object 男性角色 : Sora("男性角色", "https://sora.komica.org/38")
        object 短片2 : Sora("短片2", "https://sora.komica.org/69")
        object 寫真 : Sora("寫真", "https://sora.komica.org/16")
        object 女性角色 : Sora("女性角色", "https://luna.komica.org/19")
        object 歡樂惡搞 : Sora("歡樂惡搞", "https://luna.komica.org/12")
        object Vtuber : Sora("Vtuber", "https://luna.komica.org/74")
        object GIF : Sora("GIF", "https://luna.komica.org/23")
        object 蘿蔔 : Sora("蘿蔔", "https://aqua.komica.org/15")
        object 模型 : Sora("模型", "https://aqua.komica.org/09")
        object 鋼普拉 : Sora("鋼普拉", "https://aqua.komica.org/61")
        object 飲食 : Sora("飲食", "https://aqua.komica.org/58")
        object 特攝 : Sora("特攝", "https://aqua.komica.org/13")
        object 小說 : Sora("小說", "https://aqua.komica.org/35")
        object 遊戲速報 : Sora("遊戲速報", "https://aqua.komica.org/27")
        object `奇幻-科幻` : Sora("奇幻/科幻", "https://aqua.komica.org/60")
        object 軍武 : Sora("軍武", "https://aqua.komica.org/17")
        object 遊戲王 : Sora("遊戲王", "https://aqua.komica.org/73")
        object `電腦-消費電子` : Sora("電腦/消費電子", "https://aqua.komica.org/37")
        object 中性角色 : Sora("中性角色", "https://aqua.komica.org/57")
        object 影視 : Sora("影視", "https://aqua.komica.org/04")
        object 塗鴉王國 : Sora("塗鴉王國", "https://aqua.komica.org/30")
        object 新聞 : Sora("新聞", "https://aqua.komica.org/25")
        object 布袋戲 : Sora("布袋戲", "https://aqua.komica.org/46")
        object 紙牌 : Sora("紙牌", "https://aqua.komica.org/10")
        object 網路遊戲 : Sora("網路遊戲", "https://aqua.komica.org/52")
        object 四格 : Sora("四格", "https://tomo.komica.org/42a")
    }
    sealed class _2catKomica(name: String, url: String) : Board(name, url) {
        object 新番實況 : _2catKomica("新番實況", "https://2cat.komica.org/~tedc21thc/live")
        object 新番捏他 : _2catKomica("新番捏他", "https://2cat.komica.org/~tedc21thc/new")
        object 漫畫 : _2catKomica("漫畫", "https://2cat.komica.org/~scribe/2c")
        object 動畫 : _2catKomica("動畫", "https://2cat.komica.org/~tedc21thc/anime")
        object 車 : _2catKomica("車", "https://2cat.komica.org/~caradmin/all-car")
        object 萌 : _2catKomica("萌", "https://2cat.komica.org/~kirur/img2")
    }
    sealed class _2cat(name: String, url: String) : Board(name, url) {
        object 碧藍幻想 : _2cat("碧藍幻想", "https://2cat.org/~granblue")
        object 手機遊戲 : _2cat("手機遊戲", "https://2cat.org/~handheld")
        object `Azur-Lane` : _2cat("Azur Lane", "https://2cat.org/~azurlane")
        object 網頁遊戲 : _2cat("網頁遊戲", "https://2cat.org/~webgame")
    }
    object PAD : Board("PAD", "https://phone.mymoe.moe/bazudora")
    object 三次實況 : Board("三次實況", "https://yuutan.mymoe.moe/pawahuru")
    object 綜合2 : Board("綜合2", "https://alleyneblade.mymoe.moe/queensblade")
    object 少女前線 : Board("少女前線", "https://secilia.zawarudo.org/gf")
    object AGA : Board("AGA", "https://secilia.zawarudo.org/alicegearaegis")
    object 人外 : Board("人外", "https://komica.dbfoxtw.me/jingai")
    object 格鬥遊戲 : Board("格鬥遊戲", "https://komica.yucie.net/fight")
    object Idolmaster : Board("Idolmaster", "http://anzuchang.com")
    object `3D-STG` : Board("3D STG", "https://kagaminerin.org/fps")
    object 魔物獵人 : Board("魔物獵人", "http://strange-komica.com/MonsterHunter")
    object `TYPE-MOON` : Board("TYPE-MOON", "http://gzone-anime.info/UnitedSites/TypeMoon")
    object `Figure-GK` : Board("Figure/GK", "https://vi.anacel.com/fg/vichan/fg")
    object 艦隊收藏 : Board("艦隊收藏", "http://acgspace.wsfun.com/kancolle")
}

fun boards() =
    listOf(
        Board.Sora.綜合,
        Board.Sora.男性角色,
        Board.Sora.短片2,
        Board.Sora.寫真,
        Board.Sora.女性角色,
        Board.Sora.歡樂惡搞,
        Board.Sora.Vtuber,
        Board.Sora.GIF,
        Board.Sora.蘿蔔,
        Board.Sora.模型,
        Board.Sora.鋼普拉,
        Board.Sora.蘿蔔,
        Board.Sora.飲食,
        Board.Sora.特攝,
        Board.Sora.小說,
        Board.Sora.遊戲速報,
        Board.Sora.`奇幻-科幻`,
        Board.Sora.軍武,
        Board.Sora.遊戲王,
        Board.Sora.`電腦-消費電子`,
        Board.Sora.中性角色,
        Board.Sora.影視,
        Board.Sora.塗鴉王國,
        Board.Sora.新聞,
        Board.Sora.布袋戲,
        Board.Sora.紙牌,
        Board.Sora.網路遊戲,
        Board.Sora.四格,
        Board._2catKomica.新番實況,
        Board._2catKomica.新番捏他,
        Board._2catKomica.漫畫,
        Board._2catKomica.動畫,
        Board._2catKomica.車,
        Board._2catKomica.萌,
        Board._2cat.碧藍幻想,
        Board._2cat.手機遊戲,
        Board._2cat.`Azur-Lane`,
        Board.PAD,
        Board.三次實況,
        Board.綜合2,
        Board.少女前線,
        Board.AGA,
        Board.人外,
        Board.格鬥遊戲,
        Board.Idolmaster,
        Board.`3D-STG`,
        Board.魔物獵人,
        Board.`TYPE-MOON`,
        Board.`Figure-GK`,
        Board.艦隊收藏,
    )