import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { TUNNEL_ENDPOINT_TUNNEL_TYPE_OPTIONS, TUNNEL_ENDPOINT_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { TunnelEndpointFilter } from './types';

interface Props {
  value: TunnelEndpointFilter;
  onChange: (next: TunnelEndpointFilter) => void;
  onReset: () => void;
}

export function TunnelEndpointFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: TunnelEndpointFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Tunnel Endpoint filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Tunnel Endpoint"
          placeholder="code, name or description"
          value={value.q ?? ''}
          onChange={(v) => patch({ q: v || undefined })}
        />
      </FilterField>
      <FilterField label="Status">
        <MultiSelect
          ariaLabel="Status filter"
          options={LIFECYCLE_OPTIONS}
          value={value.status ?? []}
          onChange={(v) => patch({ status: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Code">
        <Input
          aria-label="Code filter"
          value={value.code ?? ''}
          onChange={(v) => patch({ code: v || undefined })}
        />
      </FilterField>
      <FilterField label="Name">
        <Input
          aria-label="Name filter"
          value={value.name ?? ''}
          onChange={(v) => patch({ name: v || undefined })}
        />
      </FilterField>
      <FilterField label="External Ref">
        <Input
          aria-label="External Ref filter"
          value={value.externalRef ?? ''}
          onChange={(v) => patch({ externalRef: v || undefined })}
        />
      </FilterField>
      <FilterField label="Device Id">
        <Input
          aria-label="Device Id filter"
          placeholder="UUID"
          value={value.deviceId ?? ''}
          onChange={(v) => patch({ deviceId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Tunnel Name">
        <Input
          aria-label="Tunnel Name filter"
          value={value.tunnelName ?? ''}
          onChange={(v) => patch({ tunnelName: v || undefined })}
        />
      </FilterField>
      <FilterField label="Tunnel Type">
        <MultiSelect
          ariaLabel="Tunnel Type filter"
          options={TUNNEL_ENDPOINT_TUNNEL_TYPE_OPTIONS}
          value={value.tunnelType ?? []}
          onChange={(v) => patch({ tunnelType: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Source Ip">
        <Input
          aria-label="Source Ip filter"
          value={value.sourceIp ?? ''}
          onChange={(v) => patch({ sourceIp: v || undefined })}
        />
      </FilterField>
      <FilterField label="Destination Ip">
        <Input
          aria-label="Destination Ip filter"
          value={value.destinationIp ?? ''}
          onChange={(v) => patch({ destinationIp: v || undefined })}
        />
      </FilterField>
      <FilterField label="Effective From">
        <DateRange
          ariaLabel="Effective From"
          from={value.effectiveFromFrom}
          to={value.effectiveFromTo}
          onChange={(from, to) => patch({ effectiveFromFrom: from, effectiveFromTo: to })}
        />
      </FilterField>
      <FilterField label="Effective To">
        <DateRange
          ariaLabel="Effective To"
          from={value.effectiveToFrom}
          to={value.effectiveToTo}
          onChange={(from, to) => patch({ effectiveToFrom: from, effectiveToTo: to })}
        />
      </FilterField>
      <FilterField label="Labels">
        <Input
          aria-label="Labels filter"
          value={value.labels ?? ''}
          onChange={(v) => patch({ labels: v || undefined })}
        />
      </FilterField>
      <FilterField label="Lifecycle Stage">
        <MultiSelect
          ariaLabel="Lifecycle Stage filter"
          options={TUNNEL_ENDPOINT_LIFECYCLE_STAGE_OPTIONS}
          value={value.lifecycleStage ?? []}
          onChange={(v) => patch({ lifecycleStage: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Source System">
        <Input
          aria-label="Source System filter"
          value={value.sourceSystem ?? ''}
          onChange={(v) => patch({ sourceSystem: v || undefined })}
        />
      </FilterField>
      <div className="filter-bar__actions">
        <button type="button" className="btn btn--ghost" onClick={onReset}>
          Reset filters
        </button>
      </div>
    </form>
  );
}
