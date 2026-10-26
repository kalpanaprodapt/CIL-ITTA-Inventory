import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { EVENT_SOURCE_PROTOCOL_OPTIONS, EVENT_SOURCE_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { EventSourceFilter } from './types';

interface Props {
  value: EventSourceFilter;
  onChange: (next: EventSourceFilter) => void;
  onReset: () => void;
}

export function EventSourceFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: EventSourceFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Event Source filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Event Source"
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
      <FilterField label="Source Name">
        <Input
          aria-label="Source Name filter"
          value={value.sourceName ?? ''}
          onChange={(v) => patch({ sourceName: v || undefined })}
        />
      </FilterField>
      <FilterField label="Protocol">
        <MultiSelect
          ariaLabel="Protocol filter"
          options={EVENT_SOURCE_PROTOCOL_OPTIONS}
          value={value.protocol ?? []}
          onChange={(v) => patch({ protocol: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Endpoint">
        <Input
          aria-label="Endpoint filter"
          value={value.endpoint ?? ''}
          onChange={(v) => patch({ endpoint: v || undefined })}
        />
      </FilterField>
      <FilterField label="Heartbeat Seconds">
        <RangeInputs
          ariaLabel="Heartbeat Seconds"
          min={value.heartbeatSecondsMin}
          max={value.heartbeatSecondsMax}
          onChange={(min, max) => patch({ heartbeatSecondsMin: min, heartbeatSecondsMax: max })}
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
          options={EVENT_SOURCE_LIFECYCLE_STAGE_OPTIONS}
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
      <FilterField label="Region">
        <Input
          aria-label="Region filter"
          value={value.region ?? ''}
          onChange={(v) => patch({ region: v || undefined })}
        />
      </FilterField>
      <FilterField label="Owner">
        <Input
          aria-label="Owner filter"
          value={value.owner ?? ''}
          onChange={(v) => patch({ owner: v || undefined })}
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
