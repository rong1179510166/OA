(function(){
 "use strict";
//two hand offset
var eagleHandHidedStatus = [{
    left: 45,
    bottom: 6
}, {
    left: -45,
    bottom: 6
}];

//two hand
var eagleLeftHandEL,
    eagleRightHandEL;

$(function(){
    eagleLeftHandEL = $(".hand-left");
    eagleRightHandEL = $(".hand-right");
    eagleShow();
})
$("input[type=password]").focus(function() {
    eagleLeftHandEL.animate(eagleHandHidedStatus[0], {
        step: hidingStep,
        duration: 500
    });
    eagleRightHandEL.animate(eagleHandHidedStatus[1], {
        step: hidingStep,
        duration: 500,
        complete: function() {
            setTimeout(eaglePeek, 1000);
        }
    });
}).blur(function() {
    // 立即结束动画
    eagleRightHandEL.stop(true, true);
    eagleLeftHandEL.stop(true, true);

    eagleLeftHandEL[0].hideStatus = 0;
    eagleLeftHandEL[0].className = "sprt-eagle hand-left";
    eagleLeftHandEL[0].style = "";

    eagleRightHandEL[0].hideStatus = 0;
    eagleRightHandEL[0].className = "sprt-eagle hand-right";
    eagleRightHandEL[0].style = "";

});



var eagleShow = function() {
    $(".eagel-box").animate({
        bottom: -6
    }, 1000, function() {
        this.style.zIndex = 100;
    });
};

var hidingStep = function(now, fx) {
    if(fx.prop === "bottom") {
        now = Math.floor(now);
        if(now === 3 && this.hideStatus !== 1) {
            this.classList.add("hiding");
            this.hideStatus = 1;
        } else if(now === 5 && this.hideStatus !== 2) {
            this.classList.remove("hiding");
            this.classList.add("hided");
            this.hideStatus = 2;
        }
    }
};
/**
 * the eagel peak
 */
var eaglePeek = function() {
    if(eagleRightHandEL[0].hideStatus === 2) {
        eagleRightHandEL.animate({
            bottom: 3,
            left: -25
        }, 300, function() {
            setTimeout(function() {
                if(eagleRightHandEL[0].hideStatus === 2) {
                    eagleRightHandEL.animate(eagleHandHidedStatus[1], 200);
                }
            }, 500);
        });
    }
};

})();