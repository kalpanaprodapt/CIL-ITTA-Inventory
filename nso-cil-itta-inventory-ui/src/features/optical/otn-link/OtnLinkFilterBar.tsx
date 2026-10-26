import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { OTN_LINK_ODU_OPTIONS, OTN_LINK_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { OtnLinkFilter } from './types';

interface Props {
  value: OtnLinkFilter;
  onChange: (next: OtnLinkFilter) => void;
  onReset: () => void;
}

export function OtnLinkFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: OtnLinkFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Otn Link filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Otn Link"
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
      <FilterField label="A Node Id">
        <Input
          aria-label="A Node Id filter"
          placeholder="UUID"
          value={value.aNodeId ?? ''}
          onChange={(v) => patch({ aNodeId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Z Node Id">
        <Input
          aria-label="Z Node Id filter"
          placeholder="UUID"
          value={value.zNodeId ?? ''}
          onChange={(v) => patch({ zNodeId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Odu">
        <MultiSelect
          ariaLabel="Odu filter"
          options={OTN_LINK_ODU_OPTIONS}
          value={value.odu ?? []}
          onChange={(v) => patch({ odu: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Latency Us">
        <RangeInputs
          ariaLabel="Latency Us"
          min={value.latencyUsMin}
          max={value.latencyUsMax}
          onChange={(min, max) => patch({ latencyUsMin: min, latencyUsMax: max })}
        />
      </FilterField>
      <FilterField label="Protection">
        <Input
          aria-label="Protection filter"
          value={value.protection ?? ''}
          onChange={(v) => patch({ protection: v || undefined })}
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
          options={OTN_LINK_LIFECYCLE_STAGE_OPTIONS}
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
