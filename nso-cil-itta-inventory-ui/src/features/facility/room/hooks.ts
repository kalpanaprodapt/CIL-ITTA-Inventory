import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateRooms,
  changeRoomStatus,
  createRoom,
  deleteRoom,
  exportRoomsCsv,
  getRoom,
  getRoomStatusStats,
  listRoomSummaries,
  searchRooms,
  updateRoom,
  listRoomByFloorId,
} from './api';
import type { RoomFilter, RoomInput, RoomUpdateInput } from './types';

export const roomKeys = {
  all: ['room'] as const,
  lists: () => [...roomKeys.all, 'list'] as const,
  list: (filter: RoomFilter, page: number, size: number, sort?: string) =>
    [...roomKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...roomKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...roomKeys.all, 'summaries', status] as const,
  stats: () => [...roomKeys.all, 'stats'] as const,
};

export function useRoomList(filter: RoomFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: roomKeys.list(filter, page, size, sort),
    queryFn: () => searchRooms(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useRoom(id: string | undefined) {
  return useQuery({
    queryKey: roomKeys.detail(id ?? ''),
    queryFn: () => getRoom(id as string),
    enabled: Boolean(id),
  });
}

export function useRoomSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: roomKeys.summaries(status),
    queryFn: () => listRoomSummaries(status),
    staleTime: 60_000,
  });
}

export function useRoomStatusStats() {
  return useQuery({
    queryKey: roomKeys.stats(),
    queryFn: getRoomStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateRoom() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: RoomInput) => createRoom(input),
    onSuccess: () => client.invalidateQueries({ queryKey: roomKeys.all }),
  });
}

export function useUpdateRoom(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: RoomUpdateInput) => updateRoom(id, input),
    onSuccess: (updated) => {
      client.setQueryData(roomKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: roomKeys.lists() });
    },
  });
}

export function useChangeRoomStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeRoomStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: roomKeys.all }),
  });
}

export function useDeleteRoom() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteRoom(id),
    onSuccess: () => client.invalidateQueries({ queryKey: roomKeys.all }),
  });
}

export function useBulkCreateRooms() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: RoomInput[]) => bulkCreateRooms(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: roomKeys.all }),
  });
}

export function useExportRooms() {
  return useMutation({
    mutationFn: (filter: RoomFilter) => exportRoomsCsv(filter),
  });
}

export function useRoomsByFloorId(floorId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...roomKeys.all, 'by-floor_id', floorId, page, size],
    queryFn: () => listRoomByFloorId(floorId as string, page, size),
    enabled: Boolean(floorId),
  });
}
