/**
 * @param {number[]} stones
 * @return {boolean}
 */
// 403
var canCross = function(stones) {
    if(stones.length === 0)
        return true
    let map = {}
    for(let stone of stones){
        map[stone] = new Set()
    }
    map[stones[0]].add(1)
    for(let stone of stones){
        for(let step of map[stone]) {
            let reach = step + stone
            if(reach === stones[stones.length - 1])
                return true
            if(map[reach] !== undefined){
                map[reach].add(step)
                step > 1? map[reach].add(step - 1): null
                map[reach].add(step + 1)
            }
        }
    }
    return false
};

/**
 * @param {number[]} machines
 * @return {number}
 */
 // 517
var findMinMoves = function(machines) {
};
