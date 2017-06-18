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
    let total = machines.reduce((acc, val) => {
        return acc + val 
    }, 0)
    if(total % machines.length !== 0)
        return -1
    let avg = total / machines.length
    let count = 0, max = 0
    machines.map((machine) => {
        count += machine - avg
        max = Math.max(machine - avg, Math.abs(count), max)
    })
    return max
};
/**
 * @param {string} ring
 * @param {string} key
 * @return {number}
 */
// 514
var findRotateSteps = function(ring, key) {
    let rLen = ring.length
    let kLen = key.length
    let dp = []
    for(let i = 0; i <= kLen; i++)
        dp[i] = new Array(rLen)
    for(let i = 0; i <= rLen; i++)
        dp[kLen][i] = 0
    for(let i = kLen - 1; i >= 0; i--) {
        for(let j = 0; j < rLen; j++) {
            dp[i][j] = Number.MAX_VALUE
            for(let k = 0; k < rLen; k++) {
                if(ring[k] === key[i]) {
                    let diff = Math.abs(k - j)
                    diff = Math.min(diff, rLen - diff)
                    dp[i][j] = Math.min(dp[i][j], dp[i + 1][k] + diff)
                }
            }
        }
    }
    return dp[0][0] + kLen
};
