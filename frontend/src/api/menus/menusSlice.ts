import { BaseApiSlice } from '../baseApiSlice';
import {Menu, AddMenuParameters, DeleteMenuParameters, ListMenuParameters} from './types';


const MenuApiSlice = BaseApiSlice.injectEndpoints({
    endpoints: (build) => ({
            addMenu: build.mutation<Menu, AddMenuParameters>({
                query: ({rest_id,...args}) => {
                    return {
                        url: `restaurants/${rest_id}/menu`,
                        method: 'POST',
                        body: args
                    };
                },
                invalidatesTags: [{ type: 'Menu', id: 'PARTIAL-LIST' }]
            }),
            deleteMenu: build.mutation<Menu, DeleteMenuParameters>({
                query: ({rest_id,menu_id}) => {
                    return {
                        url: `restaurants/${rest_id}/menu/${menu_id}`,
                        method: 'DELETE',
                    };
                },
                invalidatesTags: [{ type: 'Menu', id: 'PARTIAL-LIST' }]
            }),
            listMenu: build.query<Menu[], string>({
                query: ( url ) => url.toString()
            }),
        }
    )});
export const {
    useListMenuQuery: useListMenu,
    useAddMenuMutation: useAddMenu,
    useDeleteMenuMutation: useDeleteMenu
} = MenuApiSlice;