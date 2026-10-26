import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { PATCH_PANEL_PANEL_TYPE_OPTIONS, PATCH_PANEL_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { PatchPanelFilter } from './types';

interface Props {
  value: PatchPanelFilter;
  onChange: (next: PatchPanelFilter) => void;
  onReset: () => void;
}

export function PatchPanelFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: PatchPanelFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Patch Panel filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Patch Panel"
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
      <FilterField label="Room Id">
        <Input
          aria-label="Room Id filter"
          placeholder="UUID"
          value={value.roomId ?? ''}
          onChange={(v) => patch({ roomId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Rack Id">
        <Input
          aria-label="Rack Id filter"
          placeholder="UUID"
          value={value.rackId ?? ''}
          onChange={(v) => patch({ rackId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Panel Type">
        <MultiSelect
          ariaLabel="Panel Type filter"
          options={PATCH_PANEL_PANEL_TYPE_OPTIONS}
          value={value.panelType ?? []}
          onChange={(v) => patch({ panelType: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Port Count">
        <RangeInputs
          ariaLabel="Port Count"
          min={value.portCountMin}
          max={value.portCountMax}
          onChange={(min, max) => patch({ portCountMin: min, portCountMax: max })}
        />
      </FilterField>
      <FilterField label="Label">
        <Input
          aria-label="Label filter"
          value={value.label ?? ''}
          onChange={(v) => patch({ label: v || undefined })}
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
          options={PATCH_PANEL_LIFECYCLE_STAGE_OPTIONS}
          value={value.lifecycleStage ?? []}
          onChange={(v) => patch({ lifecycleStage: v.length ? v : undefined })}
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
