import { combineReducers, configureStore } from '@reduxjs/toolkit';
import { BaseApiSlice } from './api/baseApiSlice';
import authReducer from './api/auth/authSlice';
import i18nReducer from './api/i18n/i18nSlice';
import { PERSIST, persistReducer, persistStore } from 'redux-persist';
import storage from 'redux-persist/lib/storage';
import hardSet from 'redux-persist/lib/stateReconciler/hardSet';

const persistConfig = {
    key: 'i18n',
    storage,
    version: 1,
    hardSet,
    whitelist: ['i18n']
};

const baseReducers = combineReducers({
    auth: authReducer,
    i18n: i18nReducer,
    [BaseApiSlice.reducerPath]: BaseApiSlice.reducer
});

const persistedReducer = persistReducer(persistConfig, baseReducers);

const store = configureStore({
    reducer: persistedReducer,
    middleware: (getDefaultMiddleware) => {
        return getDefaultMiddleware({
            serializableCheck: {
                ignoredActions: [PERSIST]
            }
        }).concat(BaseApiSlice.middleware);
    }
});

const persistor = persistStore(store);

// Infer the `RootState` and `AppDispatch` types from the store itself
export type RootState = ReturnType<typeof store.getState>;
// Inferred type: {posts: PostsState, comments: CommentsState, users: UsersState}
export type AppDispatch = typeof store.dispatch;

export { store, persistor };