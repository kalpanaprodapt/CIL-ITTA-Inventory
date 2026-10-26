import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { PATH_TRACE_LIFECYCLE_STAGE_OPTIONS, PATH_TRACE_CRITICALITY_OPTIONS } from './types';
import type { PathTraceFilter } from './types';

interface Props {
  value: PathTraceFilter;
  onChange: (next: PathTraceFilter) => void;
  onReset: () => void;
}

export function PathTraceFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: PathTraceFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Path Trace filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Path Trace"
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
      <FilterField label="Source Ref">
        <Input
          aria-label="Source Ref filter"
          value={value.sourceRef ?? ''}
          onChange={(v) => patch({ sourceRef: v || undefined })}
        />
      </FilterField>
      <FilterField label="Target Ref">
        <Input
          aria-label="Target Ref filter"
          value={value.targetRef ?? ''}
          onChange={(v) => patch({ targetRef: v || undefined })}
        />
      </FilterField>
      <FilterField label="Hop Count">
        <RangeInputs
          ariaLabel="Hop Count"
          min={value.hopCountMin}
          max={value.hopCountMax}
          onChange={(min, max) => patch({ hopCountMin: min, hopCountMax: max })}
        />
      </FilterField>
      <FilterField label="Total Latency Ms">
        <RangeInputs
          ariaLabel="Total Latency Ms"
          min={value.totalLatencyMsMin}
          max={value.totalLatencyMsMax}
          onChange={(min, max) => patch({ totalLatencyMsMin: min, totalLatencyMsMax: max })}
        />
      </FilterField>
      <FilterField label="Trace Method">
        <Input
          aria-label="Trace Method filter"
          value={value.traceMethod ?? ''}
          onChange={(v) => patch({ traceMethod: v || undefined })}
        />
      </FilterField>
      <FilterField label="Traced At">
        <DateRange
          ariaLabel="Traced At"
          from={value.tracedAtFrom}
          to={value.tracedAtTo}
          onChange={(from, to) => patch({ tracedAtFrom: from, tracedAtTo: to })}
        />
      </FilterField>
      <FilterField label="Lifecycle Stage">
        <MultiSelect
          ariaLabel="Lifecycle Stage filter"
          options={PATH_TRACE_LIFECYCLE_STAGE_OPTIONS}
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
      <FilterField label="Criticality">
        <MultiSelect
          ariaLabel="Criticality filter"
          options={PATH_TRACE_CRITICALITY_OPTIONS}
          value={value.criticality ?? []}
          onChange={(v) => patch({ criticality: v.length ? v : undefined })}
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
