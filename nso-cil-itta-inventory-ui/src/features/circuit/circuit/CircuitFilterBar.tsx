import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { CIRCUIT_CIRCUIT_TYPE_OPTIONS, CIRCUIT_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { CircuitFilter } from './types';

interface Props {
  value: CircuitFilter;
  onChange: (next: CircuitFilter) => void;
  onReset: () => void;
}

export function CircuitFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: CircuitFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Circuit filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Circuit"
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
      <FilterField label="Circuit Id">
        <Input
          aria-label="Circuit Id filter"
          value={value.circuitId ?? ''}
          onChange={(v) => patch({ circuitId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Circuit Type">
        <MultiSelect
          ariaLabel="Circuit Type filter"
          options={CIRCUIT_CIRCUIT_TYPE_OPTIONS}
          value={value.circuitType ?? []}
          onChange={(v) => patch({ circuitType: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Bandwidth Mbps">
        <RangeInputs
          ariaLabel="Bandwidth Mbps"
          min={value.bandwidthMbpsMin}
          max={value.bandwidthMbpsMax}
          onChange={(min, max) => patch({ bandwidthMbpsMin: min, bandwidthMbpsMax: max })}
        />
      </FilterField>
      <FilterField label="Provider Id">
        <Input
          aria-label="Provider Id filter"
          placeholder="UUID"
          value={value.providerId ?? ''}
          onChange={(v) => patch({ providerId: v || undefined })}
        />
      </FilterField>
      <FilterField label="A Site Id">
        <Input
          aria-label="A Site Id filter"
          placeholder="UUID"
          value={value.aSiteId ?? ''}
          onChange={(v) => patch({ aSiteId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Z Site Id">
        <Input
          aria-label="Z Site Id filter"
          placeholder="UUID"
          value={value.zSiteId ?? ''}
          onChange={(v) => patch({ zSiteId: v || undefined })}
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
          options={CIRCUIT_LIFECYCLE_STAGE_OPTIONS}
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
