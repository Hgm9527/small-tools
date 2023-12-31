import useStore from '@/store'
import { Directive, DirectiveBinding } from 'vue'

// 自定义权限指令`v-hasPerm` `v-hasRole`

/**
 * 按钮权限校验
 * array :  v-hasPerm="['sys:user:add','sys:user:edit']"
 * single : v-hasPerm="'sys:user:add'"
 */
export const hasPerm: Directive = {
  mounted(el: HTMLElement, binding: DirectiveBinding) {
    const { user } = useStore()
    const { value } = binding

    // 当前路由
    const currentRouteUrl = window.location.hash.replace('#/', '')
    console.log('当前路由：', currentRouteUrl)
    const btnPermList = getBtnPermList(currentRouteUrl, user.permissionTreeList, [])
    console.log('拥有的按钮权限：', btnPermList)
    if (value) {
      // DOM绑定需要的按钮权限标识
      const requiredPerms = value instanceof Array ? value : [value]
      console.log('需要的按钮权限：', requiredPerms)
      const hasPerm = btnPermList.some((btnPerm) => {
        return requiredPerms.includes(btnPerm)
      })
      if (!hasPerm) {
        el.parentNode && el.parentNode.removeChild(el)
      }
    } else {
      throw new Error("need perms! Like v-has-perm=\"['sys:user:add','sys:user:edit']\"")
    }
  },
}

/**
 * 获取当前路由下的按钮权限
 * @param currentRouteUrl 当前路由url ex: system/user/index
 * @param permissionTreeList 权限菜单树
 * @param btnPermList 按钮权限
 * @returns 按钮权限
 */
function getBtnPermList(currentRouteUrl: string, permissionTreeList: any, btnPermList: Array<string>): Array<string> {
  if (permissionTreeList) {
    permissionTreeList.forEach((e: { meta: any; component: string; children: any }) => {
      if (e.component === currentRouteUrl || e.component === currentRouteUrl + '/index') {
        e.meta.btnPermList.forEach((btnPerm: string) => {
          btnPermList.push(btnPerm)
        })
      }
      const childList = e.children
      if (childList) {
        getBtnPermList(currentRouteUrl, childList, btnPermList)
      } else {
        return btnPermList
      }
    })
  }
  return btnPermList
}

/**
 * 角色权限校验
 */
export const hasRole: Directive = {
  mounted(el: HTMLElement, binding: DirectiveBinding) {
    const { value } = binding
    if (value) {
      // DOM绑定需要的角色编码
      const requiredRoles = value
      const { user } = useStore()
      const hasRole = user.roleNames.some((perm) => {
        return requiredRoles.includes(perm)
      })
      if (!hasRole) {
        el.parentNode && el.parentNode.removeChild(el)
      }
    } else {
      throw new Error("need roles! Like v-has-role=\"['admin','test']\"")
    }
  },
}
