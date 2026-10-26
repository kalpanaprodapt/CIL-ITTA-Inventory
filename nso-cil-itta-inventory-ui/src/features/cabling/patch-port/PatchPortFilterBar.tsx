import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { PATCH_PORT_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { PatchPortFilter } from './types';

interface Props {
  value: PatchPortFilter;
  onChange: (next: PatchPortFilter) => void;
  onReset: () => void;
}

export function PatchPortFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: PatchPortFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Patch Port filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Patch Port"
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
      <FilterField label="Panel Id">
        <Input
          aria-label="Panel Id filter"
          placeholder="UUID"
          value={value.panelId ?? ''}
          onChange={(v) => patch({ panelId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Port Number">
        <RangeInputs
          ariaLabel="Port Number"
          min={value.portNumberMin}
          max={value.portNumberMax}
          onChange={(min, max) => patch({ portNumberMin: min, portNumberMax: max })}
        />
      </FilterField>
      <FilterField label="Connected Cable Id">
        <Input
          aria-label="Connected Cable Id filter"
          placeholder="UUID"
          value={value.connectedCableId ?? ''}
          onChange={(v) => patch({ connectedCableId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Polarity">
        <Input
          aria-label="Polarity filter"
          value={value.polarity ?? ''}
          onChange={(v) => patch({ polarity: v || undefined })}
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
          options={PATCH_PORT_LIFECYCLE_STAGE_OPTIONS}
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
      <div className="filter-bar__actions">
        <button type="button" className="btn btn--ghost" onClick={onReset}>
          Reset filters
        </button>
      </div>
    </form>
  );
}
